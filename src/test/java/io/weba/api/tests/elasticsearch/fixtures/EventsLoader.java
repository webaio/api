package io.weba.api.tests.elasticsearch.fixtures;

import io.weba.api.tests.common.utils.ResourcesContentLoader;
import io.weba.api.tests.elasticsearch.fixtures.exception.LoaderException;
import io.weba.api.ui.rest.config.properties.ElasticsearchProperties;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Service
public class EventsLoader implements Loader {
    private Client client;

    private ElasticsearchProperties elasticsearchProperties;

    @Autowired
    public EventsLoader(Client client, ElasticsearchProperties elasticsearchProperties) {
        this.client = client;
        this.elasticsearchProperties = elasticsearchProperties;
    }

    @Override
    public void load(String type, String source) throws LoaderException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Map<String, Object>> fixtures = null;

        try {
            fixtures = mapper.readValue(
                    ResourcesContentLoader.load(String.format("elasticsearch_fixtures/%s.json", source)),
                    ArrayList.class
            );
        } catch (IOException e) {
            throw new LoaderException(e.getMessage(), e.getCause());
        }

        BulkRequestBuilder bulkRequest = client.prepareBulk();
        bulkRequest.setRefresh(true);
        for (Map<String, Object> fixture : fixtures) {
            IndexRequestBuilder indexRequest = client.prepareIndex()
                    .setId((String) fixture.get("id"))
                    .setType(type)
                    .setIndex(elasticsearchProperties.getIndexName())
                    .setSource(fixture);

            bulkRequest.add(indexRequest);
        }

        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        if (bulkResponse.hasFailures()) {
            throw new RuntimeException(bulkResponse.buildFailureMessage());
        }
    }
}
