package io.weba.api.infrastructure.elasticsearch;

import org.junit.Before;

abstract public class EventsTestSuite extends TestSuite {
    @Before
    public void setUp() throws Exception {
        dropIndex(elasticsearchProperties.getEventsIndexName());
        createIndex(elasticsearchProperties.getEventsIndexName());
    }
}
