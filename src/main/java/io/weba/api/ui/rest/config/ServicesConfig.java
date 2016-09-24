package io.weba.api.ui.rest.config;

import io.weba.api.ui.rest.service.AppEnvironmentImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfig {
    @Bean
    public ClassLoader classLoader() {
        return getClass().getClassLoader();
    }

    @Bean
    public AppEnvironmentImpl appEnvironment() {
         return new AppEnvironmentImpl();
    }
}
