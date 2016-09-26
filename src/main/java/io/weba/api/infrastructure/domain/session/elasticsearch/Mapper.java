package io.weba.api.infrastructure.domain.session.elasticsearch;

import io.weba.api.domain.session.SessionCardinality;
import org.elasticsearch.action.search.SearchResponse;

import java.util.List;

public interface Mapper {
    List<SessionCardinality> mapResult(SearchResponse response);
}
