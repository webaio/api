package io.weba.api.tests.common.suite;

import io.weba.api.tests.elasticsearch.index.Utils;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

abstract public class TestSuite {
    @Autowired
    protected Node node;

    @Autowired
    protected Client client;

    @Autowired
    protected Utils utils;

    @After
    public void tearDown() throws Exception {
        if (!node.isClosed()) {
            client.close();
            node.close();
        }
    }
}
