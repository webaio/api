package io.weba.api.application.event;

import io.weba.api.application.base.DomainEvent;
import io.weba.api.domain.account.AccountId;
import io.weba.api.domain.role.RoleId;
import io.weba.api.domain.user.UserId;
import io.weba.api.infrastructure.validator.UUID;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddUserEvent implements DomainEvent {
    @NotNull
    @Size(min = 2, max = 30)
    public String firstName;

    @NotNull
    @Size(min = 2, max = 30)
    public String lastName;

    @Email
    public String email;

    @NotNull
    @Size(min = 2, max = 50)
    public String password;

    @NotNull
    @UUID
    public AccountId accountId;

    @NotNull
    @UUID
    public RoleId roleId;

    @NotNull
    @UUID
    private UserId userId;

    public AddUserEvent() {
        this.userId = new UserId(UserId.generate());
    }

    public AddUserEvent(UserId userId) {
        this.userId = userId;
    }

    public String firstName() {
        return this.firstName;
    }

    public String lastName() {
        return this.lastName;
    }

    public UserId userId() {
        return this.userId;
    }

    public String email() {
        return this.email;
    }

    public String password() {
        return this.password;
    }

    public AccountId accountId() {
        return this.accountId;
    }

    public RoleId roleId() {
        return this.roleId;
    }
}
