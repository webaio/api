package io.weba.api.tests.elasticsearch;

import io.weba.api.ui.rest.config.properties.ElasticsearchProperties;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.net.UnknownHostException;

public class ClientConfig {
    @Autowired
    private ElasticsearchProperties elasticsearchProperties;

    @Bean(name = "elasticsearchNode")
    public Node getNode() {
        Settings.Builder settingsBuilder = Settings
                .settingsBuilder();

        String rootBuildPath = System.getProperty("user.dir") + "/build";
        String esDataHome = rootBuildPath + "/elasticsearch";
        String esDataPath = rootBuildPath + "/elasticsearch/data";

        new File(esDataPath).mkdirs();

        settingsBuilder.put("node.name", ClientConfig.class);
        settingsBuilder.put("path.home", esDataHome);
        settingsBuilder.put("path.data", esDataPath);
        settingsBuilder.put("http.enabled", false);

        Settings settings = settingsBuilder.build();

        return NodeBuilder
                .nodeBuilder()
                .settings(settings)
                .data(true)
                .local(true)
                .clusterName(elasticsearchProperties.getClusterName())
                .node();
    }

    @Bean(name = "elasticsearchClient")
    public Client getClient() throws UnknownHostException {
        return getNode().client();
    }
}
