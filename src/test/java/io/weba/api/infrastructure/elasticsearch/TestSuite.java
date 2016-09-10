package io.weba.api.infrastructure.elasticsearch;

import io.weba.api.rest.config.properties.ElasticsearchProperties;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.IndexNotFoundException;
import org.elasticsearch.node.Node;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

abstract public class TestSuite {
    @Autowired
    protected Node node;

    @Autowired
    protected Client client;

    @Autowired
    protected ElasticsearchProperties elasticsearchProperties;

    @After
    public void tearDown() throws Exception {
        client.close();
        node.close();
    }

    protected void dropIndex(String indexName) {
        try {
            DeleteIndexRequest indexRequest = new DeleteIndexRequest(indexName);
            client.admin().indices().delete(indexRequest).actionGet();
        } catch(IndexNotFoundException exception) { }
    }

    protected void createIndex(String indexName) throws IOException {
        Settings.Builder settingsBuilder = Settings
                .settingsBuilder();

        settingsBuilder.put("number_of_shards", 1);
        settingsBuilder.put("number_of_replicas", 1);

        CreateIndexRequest indexRequest = new CreateIndexRequest(
                indexName,
                settingsBuilder.build()
        );
        indexRequest.mapping(indexName, getResourceContent(String.format("elasticsearch_mappings/%s.js", indexName)));

        client.admin().indices().create(indexRequest).actionGet();
    }

    protected void loadFixtures(String indexName, String fixtureName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Map<String, Object>> fixtures = mapper.readValue(
                getResourceContent(String.format("elasticsearch_fixtures/%s.js", fixtureName)),
                ArrayList.class
        );

        BulkRequestBuilder bulkRequest = client.prepareBulk();
        bulkRequest.setRefresh(true);
        for(Map<String, Object> fixture : fixtures) {
            IndexRequestBuilder indexRequest = client.prepareIndex()
                    .setId((String)fixture.get("id"))
                    .setType(indexName)
                    .setIndex(indexName)
                    .setSource(fixture);

            bulkRequest.add(indexRequest);
        }

        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        if(bulkResponse.hasFailures()) {
            throw new RuntimeException(bulkResponse.buildFailureMessage());
        }
    }

    protected String getResourceContent(String path) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(path).getFile());

        return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
    }
}
