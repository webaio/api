package io.weba.api.tests.elasticsearch.fixtures;

import io.weba.api.tests.elasticsearch.fixtures.exception.LoaderException;

public interface Loader {
    void load(String index, String source) throws LoaderException;
}
