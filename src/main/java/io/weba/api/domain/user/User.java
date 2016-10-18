package io.weba.api.domain.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.weba.api.domain.account.Account;
import io.weba.api.domain.role.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.weba.api.ui.rest.view.View;

@Table(name = "users")
@Entity
public class User implements Serializable {
    @Id
    @Column
    @JsonView({View.UserMe.class, View.AccountGet.class})
    private UUID id;

    @Column(name = "username")
    @JsonView({View.UserMe.class, View.AccountGet.class})
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    @JsonView({View.UserMe.class, View.AccountGet.class})
    private String firstName;

    @Column(name = "last_name")
    @JsonView({View.UserMe.class, View.AccountGet.class})
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonView(View.UserMe.class)
    private Role role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_accounts",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "account_id") })
    @JsonView(View.UserMe.class)
    private Set<Account> accounts = new HashSet<>();

    @Column(name = "enabled")
    @JsonView(View.UserMe.class)
    private Boolean enabled = true;

    public User(UUID userId, String username, String password, String firstName, String lastName, Role role) {
        this.id = userId;
        this.username = username;
        this.password = password;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account accounts) {
        this.accounts.add(accounts);
    }
}
