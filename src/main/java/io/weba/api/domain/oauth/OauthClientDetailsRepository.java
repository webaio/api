package io.weba.api.domain.oauth;

public interface OauthClientDetailsRepository {
    void add(OauthClientDetails oauthClientDetails);

    OauthClientDetails findBy(String clientId);
}
