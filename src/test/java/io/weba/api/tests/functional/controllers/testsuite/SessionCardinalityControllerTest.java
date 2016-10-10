package io.weba.api.tests.functional.controllers.testsuite;

import io.weba.api.infrastructure.domain.session.elasticsearch.SessionCardinalityRepositoryImpl;
import io.weba.api.tests.common.config.IntegrationTestConfig;
import io.weba.api.tests.common.suite.WebTestSuite;
import io.weba.api.tests.elasticsearch.fixtures.EventsLoader;
import io.weba.api.ui.rest.application.SpringApplication;
import io.weba.api.ui.rest.config.properties.ElasticsearchProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Objects;

import static io.weba.api.tests.oauth.AuthorizationPostProcessor.oauth;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {SpringApplication.class, IntegrationTestConfig.class}, loader = SpringBootContextLoader.class)
@WebAppConfiguration
@ActiveProfiles("testing")
@RunWith(SpringJUnit4ClassRunner.class)
public class SessionCardinalityControllerTest extends WebTestSuite {
    @Autowired
    private SessionCardinalityRepositoryImpl repository;

    @Autowired
    private ElasticsearchProperties elasticsearchProperties;

    @Autowired
    private EventsLoader eventsLoader;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        utils.recreate(elasticsearchProperties.getIndexName());
        eventsLoader.load(elasticsearchProperties.getEventsTypeName(), "session_cardinality");
    }

    @Test
    public void userHaveAccessToProtectedResource() throws Exception {
        RequestBuilder request = createRequest(
                "user@weba.io",
                "test",
                "2016-01-01",
                "2016-12-01",
                "f878da1c-822e-4046-86cb-3e515ebcdce0"
        );

        mvc
                .perform(request)
                .andExpect(status().is(200));
    }

    @Test
    public void adminHaveAccessToProtectedResource() throws Exception {
        RequestBuilder request = createRequest(
                "admin@weba.io",
                "test",
                "2016-01-01",
                "2016-12-01",
                "f878da1c-822e-4046-86cb-3e515ebcdce0"
        );

        mvc
                .perform(request)
                .andExpect(status().is(200));
    }

    @Test
    public void superAdminHaveAccessToProtectedResource() throws Exception {
        RequestBuilder request = createRequest(
                "super_admin@weba.io",
                "test",
                "2016-01-01",
                "2016-12-01",
                "f878da1c-822e-4046-86cb-3e515ebcdce0"
        );

        mvc
                .perform(request)
                .andExpect(status().is(200));
    }

    @Test
    public void dateFromShouldShouldBeRequiredParameter() throws Exception {
        RequestBuilder request = createRequest(
                "super_admin@weba.io",
                "test",
                "2016-01-01",
                null,
                "f878da1c-822e-4046-86cb-3e515ebcdce0"
        );

        mvc
                .perform(request)
                .andExpect(status().is(400));
    }

    @Test
    public void dateToShouldShouldBeRequiredParameter() throws Exception {
        RequestBuilder request = createRequest(
                "super_admin@weba.io",
                "test",
                null,
                "2016-12-01",
                "f878da1c-822e-4046-86cb-3e515ebcdce0"
        );

        mvc
                .perform(request)
                .andExpect(status().is(400));
    }


    @Test
    public void trackerIdentityShouldShouldBeRequiredParameter() throws Exception {
        RequestBuilder request = createRequest(
                "super_admin@weba.io",
                "test",
                "2016-01-01",
                "2016-12-01",
                null
        );

        mvc
                .perform(request)
                .andExpect(status().is(400));
    }

    @Test
    public void anonymousUserShouldNotHaveAccessToResource() throws Exception {
        mvc
                .perform(get("/api/session/cardinality"))
                .andExpect(status().is(401));
    }

    private MockHttpServletRequestBuilder createRequest(String username, String password, String dateFrom, String dateTo, String trackerIdentity) {
        MockHttpServletRequestBuilder requestBuilder = get("/api/session/cardinality");
        if (Objects.nonNull(username) && Objects.nonNull(password)) {
            requestBuilder.with(oauth(username, password));
        }

        if (Objects.nonNull(dateFrom)) {
            requestBuilder.param("dateFrom", dateFrom);
        }

        if (Objects.nonNull(dateTo)) {
            requestBuilder.param("dateTo", dateTo);
        }

        if (Objects.nonNull(trackerIdentity)) {
            requestBuilder.param("trackerIdentity", trackerIdentity);
        }

        return requestBuilder;
    }
}
