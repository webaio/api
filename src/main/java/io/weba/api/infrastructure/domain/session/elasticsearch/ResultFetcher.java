package io.weba.api.infrastructure.domain.session.elasticsearch;

import io.weba.api.domain.session.SessionCardinalityCriteria;
import org.elasticsearch.action.search.SearchResponse;

public interface ResultFetcher {
    SearchResponse fetchResponse(SessionCardinalityCriteria sessionCardinalityCriteria);
}
