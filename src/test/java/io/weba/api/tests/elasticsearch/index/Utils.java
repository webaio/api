package io.weba.api.tests.elasticsearch.index;

import io.weba.api.tests.common.utils.ResourcesContentLoader;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.IndexNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Utils {
    @Autowired
    protected Client client;

    public void drop(String index) {
        try {
            DeleteIndexRequest indexRequest = new DeleteIndexRequest(index);
            client.admin().indices().delete(indexRequest).actionGet();
        } catch (IndexNotFoundException exception) {
        }
    }

    public void create(String index) throws IOException {
        Settings.Builder settingsBuilder = Settings
                .settingsBuilder();

        settingsBuilder.put("number_of_shards", 1);
        settingsBuilder.put("number_of_replicas", 1);

        CreateIndexRequest indexRequest = new CreateIndexRequest(
                index,
                settingsBuilder.build()
        );
        indexRequest.source(ResourcesContentLoader.load(
                String.format("elasticsearch_sources/%s.js", index))
        );

        client.admin().indices().create(indexRequest).actionGet();
    }

    public void recreate(String index) throws IOException {
        drop(index);
        create(index);
    }
}
