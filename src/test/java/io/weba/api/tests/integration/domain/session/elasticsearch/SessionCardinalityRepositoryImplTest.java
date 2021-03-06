package io.weba.api.tests.integration.domain.session.elasticsearch;

import io.weba.api.domain.session.SessionCardinality;
import io.weba.api.domain.session.SessionCardinalityCriteria;
import io.weba.api.infrastructure.domain.session.elasticsearch.SessionCardinalityRepositoryImpl;
import io.weba.api.tests.common.suite.TestSuite;
import io.weba.api.tests.elasticsearch.config.ElasticsearchClientConfig;
import io.weba.api.tests.elasticsearch.fixtures.EventsLoader;
import io.weba.api.ui.rest.application.SpringApplication;
import io.weba.api.ui.rest.config.properties.ElasticsearchProperties;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@ContextConfiguration(classes = {SpringApplication.class, ElasticsearchClientConfig.class}, loader = SpringBootContextLoader.class)
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class SessionCardinalityRepositoryImplTest extends TestSuite {
    @Autowired
    private SessionCardinalityRepositoryImpl repository;

    @Autowired
    private ElasticsearchProperties elasticsearchProperties;

    @Autowired
    private EventsLoader eventsLoader;

    @Before
    public void setUp() throws Exception {
        utils.recreate(elasticsearchProperties.getIndexName());
        eventsLoader.load(elasticsearchProperties.getEventsTypeName(), "session_cardinality");
    }

    @Test
    public void itShouldReturnSessionCardinalityList() throws IOException {
        Date from = new DateTime("2016-01-01").toDate();
        Date to = new DateTime("2016-12-30").toDate();
        UUID trackerId = UUID.fromString("f878da1c-822e-4046-86cb-3e515ebcdce0");

        List<SessionCardinality> cardinalityList = repository.findBy(new SessionCardinalityCriteria(from, to, trackerId));
        Assert.assertEquals(2, cardinalityList.size());
    }

    @Test
    public void itShouldReturnEmptyListIfTrackerIdentityDoesNotExists() throws IOException {
        Date from = new DateTime("2016-01-19").toDate();
        Date to = new DateTime("2016-12-20").toDate();
        UUID trackerId = UUID.fromString("67f7caf3-c4a7-4346-b802-a05b4260c2a7");

        List<SessionCardinality> cardinalityList = repository.findBy(new SessionCardinalityCriteria(from, to, trackerId));
        Assert.assertEquals(0, cardinalityList.size());
    }
}
