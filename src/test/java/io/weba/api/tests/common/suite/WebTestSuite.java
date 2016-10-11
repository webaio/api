package io.weba.api.tests.common.suite;

import io.weba.api.tests.oauth.Authorization;
import io.weba.api.tests.oauth.AuthorizationPostProcessor;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

abstract public class WebTestSuite extends TestSuite {
    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected Authorization authorization;

    @Before
    public void setUp() throws Exception {
        AuthorizationPostProcessor.authorization = authorization;
    }
}
