package io.weba.api.ui.rest.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="elasticsearch", locations="classpath:elasticsearch.properties")
public class ElasticsearchProperties {
    private String nodes;
    private String clusterName;
    private String eventsIndexName;

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

    public String getEventsIndexName() {
        return eventsIndexName;
    }

    public void setEventsIndexName(String eventsIndexName) {
        this.eventsIndexName = eventsIndexName;
    }
}
