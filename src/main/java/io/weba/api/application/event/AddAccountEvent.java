package io.weba.api.application.event;

import io.weba.api.application.base.DomainEvent;

import java.util.UUID;

import io.weba.api.infrastructure.validator.UUIDConstraint;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddAccountEvent implements DomainEvent {
    @NotNull
    @Size(min = 2, max = 30)
    public String name;

    @NotNull
    @UUIDConstraint
    private UUID accountId;

    public AddAccountEvent() {
        this.accountId = UUID.randomUUID();
    }

    public AddAccountEvent(UUID accountId) {
        this.accountId = accountId;
    }

    public String name() {
        return this.name;
    }

    public UUID accountId() {
        return this.accountId;
    }
}
