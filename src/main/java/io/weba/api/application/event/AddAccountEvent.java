package io.weba.api.application.event;

import io.weba.api.domain.account.AccountId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddAccountEvent {
    @NotNull
    @Size(min=2, max=100)
    public String name;

    @NotNull
    private AccountId accountId;

    public AddAccountEvent() {
        this.accountId = new AccountId(AccountId.generate());
    }

    public String name() {
        return this.name;
    }

    public AccountId accountId() {
        return this.accountId;
    }
}
