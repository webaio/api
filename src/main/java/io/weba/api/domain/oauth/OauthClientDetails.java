package io.weba.api.domain.oauth;

import javax.persistence.*;

@Table(name = "oauth_client_details")
@Entity
public class OauthClientDetails {
    @Id
    @Column
    @Basic
    private String clientId;

    @Column(name = "name")
    private String resourceIds;

    @Column(name = "resource_ids")
    private String clientSecret;

    @Column(name = "scope")
    private String scope;

    @Column(name = "authorized_grant_types")
    private String authorizedGrantTypes;

    @Column(name = "web_server_redirect_url")
    private String webServerRedirectUrl;

    @Column(name = "authorities")
    private String authorities;

    @Column(name = "access_token_validity")
    private Integer accessTokenValidity;

    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValidity;

    @Column(name = "additional_information")
    private String additionalInformation;

    @Column(name = "autoapprove")
    private String autoapprove;

    public OauthClientDetails(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.resourceIds = null;
        this.scope = "read,write";
        this.authorizedGrantTypes = "password,authorization_code,refresh_token";
        this.webServerRedirectUrl = null;
        this.authorities = null;
        this.accessTokenValidity = 3600;
        this.refreshTokenValidity = 2592000;
        this.autoapprove = null;
        this.additionalInformation = "{}";
    }

    public OauthClientDetails() {}

    public String getClientId() {
        return clientId;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getScope() {
        return scope;
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public String getWebServerRedirectUrl() {
        return webServerRedirectUrl;
    }

    public String getAuthorities() {
        return authorities;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public String getAutoapprove() {
        return autoapprove;
    }
}
