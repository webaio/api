package io.weba.api.application.event;

import io.weba.api.application.base.DomainEvent;
import java.util.UUID;

import io.weba.api.domain.account.Account;
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
    public String username;

    @NotNull
    @Size(min = 2, max = 50)
    public String password;

    public Account account;

    public String role;

    private UUID userId;

    public AddUserEvent() {
        this.userId = UUID.randomUUID();
    }

    public AddUserEvent(UUID userId) {
        this.userId = userId;
    }

    public String firstName() {
        return this.firstName;
    }

    public String lastName() {
        return this.lastName;
    }

    public UUID userId() {
        return this.userId;
    }

    public String username() {
        return this.username;
    }

    public String password() {
        return this.password;
    }

    public Account account() {
        return this.account;
    }

    public String role() {
        return this.role;
    }
}
