package io.weba.api.ui.rest.service;

import org.springframework.stereotype.Component;

@Component
public class AppEnvironmentImpl implements AppEnvironment {
    @Override
    public String getName() {
        return System.getenv("weba.env") != null ? System.getenv("weba.env") : "production";
    }
}
