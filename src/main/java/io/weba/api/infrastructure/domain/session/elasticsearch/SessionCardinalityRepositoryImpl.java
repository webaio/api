package io.weba.api.infrastructure.domain.session.elasticsearch;

import io.weba.api.domain.session.SessionCardinality;
import io.weba.api.domain.session.SessionCardinalityCriteria;
import io.weba.api.domain.session.SessionCardinalityRepository;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public class SessionCardinalityRepositoryImpl implements SessionCardinalityRepository {
    private ResultFetcher fetcher;
    private Mapper mapper;

    @Autowired
    public SessionCardinalityRepositoryImpl(ResultFetcher fetcher, Mapper mapper) {
        this.fetcher = fetcher;
        this.mapper = mapper;
    }

    @Override
    public List<SessionCardinality> findBy(SessionCardinalityCriteria sessionCardinalityCriteria) {
        SearchResponse response = fetcher.fetchResponse(sessionCardinalityCriteria);

        return mapper.mapResult(response);
    }
}
