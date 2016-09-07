package io.weba.api.domain.user;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.role.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Table(name = "users")
@Entity
public class User implements Serializable {
    @Id
    @Column
    @Basic
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public User(UUID userId, String email, String password, Account account, String firstName, String lastName, Role role) {
        this.id = userId;
        this.email = email;
        this.password = password;
        this.account = account;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public User() {
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRoles() {
        return this.role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Account getAccount() {
        return account;
    }
}
