package io.weba.api.mock;

import io.weba.api.ui.rest.service.AppEnvironment;

public class AppEnvironmentImpl implements AppEnvironment {
    public String getName() {
        return "testing";
    }
}
