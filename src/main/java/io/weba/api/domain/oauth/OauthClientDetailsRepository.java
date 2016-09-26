package io.weba.api.domain.oauth;

import java.util.Optional;

public interface OauthClientDetailsRepository {
    void add(OauthClientDetails oauthClientDetails);

    Optional<OauthClientDetails> findBy(String clientId);
}
