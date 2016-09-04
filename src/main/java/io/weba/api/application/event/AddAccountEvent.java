package io.weba.api.application.event;

import io.weba.api.application.base.DomainEvent;
import io.weba.api.domain.account.AccountId;
import io.weba.api.infrastructure.validator.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddAccountEvent implements DomainEvent {
    @NotNull
    @Size(min = 2, max = 30)
    public String name;

    @NotNull
    @UUID
    private AccountId accountId;

    public AddAccountEvent() {
        this.accountId = new AccountId(AccountId.generate());
    }

    public AddAccountEvent(AccountId accountId) {
        this.accountId = accountId;
    }

    public String name() {
        return this.name;
    }

    public AccountId accountId() {
        return this.accountId;
    }
}
