package io.weba.api.tests.oauth;

import com.google.gson.Gson;
import io.weba.api.ui.rest.initializer.UserManagementDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.Base64Utils;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Service
public class Authorization {
    private MockMvc mvc;
    private Gson gson;

    @Autowired
    public Authorization(MockMvc mvc, Gson gson) {
        this.mvc = mvc;
        this.gson = gson;
    }

    public Response authorize(String username, String password) throws Exception {
        MockHttpServletRequestBuilder request = post(new URI("/oauth/token"));
        request.header(
                "Authorization",
                String.format("Basic %s",
                        new String(Base64Utils.encode(String.format("%s:%s", UserManagementDataLoader.OAUTH_CLIENT_UUID, UserManagementDataLoader.OAUTH_SECRET_UUID).getBytes()))
                )
        );
        request.param("grant_type", "password");
        request.param("username", username);
        request.param("password", password);
        request.contentType("application/x-www-form-urlencoded; charset=utf-8");

        MockHttpServletResponse response = mvc
                .perform(request)
                .andExpect(status().is(200))
                .andReturn()
                .getResponse();

        return gson.fromJson(response.getContentAsString(), Response.class);
    }

    public class Response {
        public final String accessToken;
        public final String tokenType;
        public final String refreshToken;
        public final int expiresIn;
        public final String scope;
        public final String jti;

        public Response(String accessToken, String tokenType, String refreshToken, int expiresIn, String scope, String jti) {
            this.accessToken = accessToken;
            this.tokenType = tokenType;
            this.refreshToken = refreshToken;
            this.expiresIn = expiresIn;
            this.scope = scope;
            this.jti = jti;
        }
    }
}
