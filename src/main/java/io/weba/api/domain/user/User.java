package io.weba.api.domain.user;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.role.Role;
import io.weba.api.infrastructure.postgres.type.UserIdType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@TypeDefs({
        @TypeDef(name = "user_id", typeClass = UserIdType.class)
})
@Table(name = "users")
@Entity
public class User implements Serializable {
    @Id
    @Column
    @Basic
    @Type(type = "user_id")
    private UserId id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @ManyToOne
    @JoinColumn(name ="account_id")
    private Account account;

    public User(UserId userId, String email, String password, Account account, String firstName, String lastName) {
        this.id = userId;
        this.email = email;
        this.password = password;
        this.account = account;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = new ArrayList<>();
    }

    public User() {
        this.roles = new ArrayList<>();
    }

    public UserId getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }

    public Collection<Role> getRoles() {
        return this.roles;
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
