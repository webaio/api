package io.weba.api.ui.rest.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "elasticsearch", locations = "classpath:elasticsearch.properties")
public class ElasticsearchProperties {
    private String nodes;
    private String clusterName;
    private String indexName;
    private String eventsTypeName;

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getEventsTypeName() {
        return eventsTypeName;
    }

    public void setEventsTypeName(String eventsTypeName) {
        this.eventsTypeName = eventsTypeName;
    }
}
