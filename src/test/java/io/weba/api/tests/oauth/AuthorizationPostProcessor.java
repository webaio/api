package io.weba.api.tests.oauth;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.Objects;

public class AuthorizationPostProcessor implements RequestPostProcessor {
    public static Authorization authorization;

    private String username;
    private String password;

    public AuthorizationPostProcessor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
        Authorization.Response response = null;
        try {
            response = authorization.authorize(username, password);
        } catch (Exception e) {
        }

        if (!Objects.isNull(request)) {
            request.addParameter("access_token", response.accessToken);
        }

        return request;
    }

    public static AuthorizationPostProcessor oauth(String username, String password) {
        return new AuthorizationPostProcessor(username, password);
    }
}
