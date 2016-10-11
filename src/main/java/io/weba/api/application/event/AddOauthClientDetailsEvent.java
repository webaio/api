package io.weba.api.application.event;

import io.weba.api.application.base.DomainEvent;

import java.util.UUID;

public class AddOauthClientDetailsEvent implements DomainEvent {
    private UUID clientId;
    private UUID clientSecret;

    public AddOauthClientDetailsEvent(UUID clientId, UUID clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public UUID clientSecret() {
        return clientSecret;
    }

    public UUID clientId() {
        return clientId;
    }
}
