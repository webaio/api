package io.weba.api.domain.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonView;
import io.weba.api.domain.site.Site;
import io.weba.api.domain.user.User;
import io.weba.api.ui.rest.view.View;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table(name = "accounts")
@Entity
public class Account implements Serializable {
    @Id
    @Column
    @JsonView({View.UserMe.class, View.AccountGet.class, View.AccountGetAll.class})
    private UUID id;

    @Column(name = "name")
    @JsonView({View.UserMe.class, View.AccountGet.class, View.AccountGetAll.class})
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
    @JsonView({View.UserMe.class, View.AccountGet.class})
    private Set<Site> sites;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_accounts",
            joinColumns = { @JoinColumn(name = "account_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    @JsonView({View.UserMe.class, View.AccountGet.class})
    private Set<User> users = new HashSet<>();

    public Account(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public Account() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Site> getSites() {
        return this.sites;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public void add(User user) {
        this.users.add(user);
    }
}
