package io.weba.api.domain.user;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.role.Role;
import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "users")
@Entity
public class User implements Serializable {
    @Id
    @Column
    private UUID id;

    @Column(name = "username")
    private String username;

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

    @Column(name = "enabled")
    private Boolean enabled = true;

    public User(UUID userId, String username, String password, Account account, String firstName, String lastName, Role role) {
        this.id = userId;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @JsonProperty(value = "password")
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

    public Boolean getEnabled() {
        return enabled;
    }
}
