package io.weba.api.application.event;

import io.weba.api.application.base.DomainEvent;
import io.weba.api.infrastructure.validator.UUIDConstraint;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class AddOuathClientDetailsEvent implements DomainEvent {
    @NotNull
    @UUIDConstraint
    private UUID clientId;

    @NotNull
    @UUIDConstraint
    private UUID clientSecret;

    public UUID clientSecret() {
        return clientSecret;
    }

    public UUID clientId() {
        return clientId;
    }
}
