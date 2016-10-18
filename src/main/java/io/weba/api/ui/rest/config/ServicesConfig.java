package io.weba.api.ui.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class ServicesConfig {
    @Bean
    public ClassLoader classLoader() {
        return getClass().getClassLoader();
    }
}
