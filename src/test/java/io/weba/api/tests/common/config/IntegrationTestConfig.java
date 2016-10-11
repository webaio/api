package io.weba.api.tests.common.config;

import io.weba.api.tests.elasticsearch.config.ElasticsearchClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({GsonConfig.class, MvcConfig.class, ElasticsearchClientConfig.class})
public class IntegrationTestConfig {
}
