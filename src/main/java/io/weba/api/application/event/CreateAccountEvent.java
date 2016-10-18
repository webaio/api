package io.weba.api.application.event;

import io.weba.api.application.base.DomainEvent;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;
import io.weba.api.ui.rest.view.View;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public final class CreateAccountEvent implements DomainEvent {
    @NotNull
    @Size(min = 2, max = 30)
    public String name;

    private UUID accountId;

    public CreateAccountEvent() {
        this.accountId = UUID.randomUUID();
    }

    public CreateAccountEvent(UUID accountId) {
        this.accountId = accountId;
    }

    public String name() {
        return this.name;
    }

    @JsonView(View.AccountCreate.class)
    public UUID accountId() {
        return this.accountId;
    }
}
