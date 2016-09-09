package io.weba.api.ui.rest.config;

import io.weba.api.ui.rest.config.properties.ElasticsearchProperties;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.regex.Pattern;

@Configuration
public class ElasticsearchClientConfig {
    private ElasticsearchProperties elasticsearchProperties;
    private Client client;

    @Autowired
    public ElasticsearchClientConfig(ElasticsearchProperties elasticsearchProperties) {
        this.elasticsearchProperties = elasticsearchProperties;
    }

    @Bean(name = "elasticsearchClient")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Client getClient() throws UnknownHostException {
        if (Objects.isNull(client)) {
            client = create();
        }

        return client;
    }

    @PreDestroy
    public void closeClient() {
        client.close();
    }

    private Client create() throws UnknownHostException {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", elasticsearchProperties.getClusterName())
                .build();

        TransportClient client = TransportClient
                .builder()
                .settings(settings)
                .build();

        String[] esServersNames = elasticsearchProperties
                .getNodes()
                .split(Pattern.quote(","));

        for (String esServerName : esServersNames) {
            int colonIndexOf = esServerName.indexOf(":");
            if (colonIndexOf != -1) {
                client.addTransportAddress(new InetSocketTransportAddress(
                        InetAddress.getByName(esServerName.substring(0, colonIndexOf)),
                        Integer.parseInt(esServerName.substring(colonIndexOf + 1, esServerName.length())))
                );
            }
        }

        return client;
    }
}
