package io.weba.api.domain.session;

import java.util.List;

public interface SessionCardinalityRepository {
    List<SessionCardinality> findBy(SessionCardinalityCriteria sessionCardinalityCriteria);
}
