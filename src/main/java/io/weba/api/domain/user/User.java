package io.weba.api.domain.user;

import org.hibernate.annotations.Type;

import javax.persistence.*;

public class User {
    @Id
    @Column
    @Basic
    @Type(type = "user_id")
    private UserId userId;

    @Column(name = "email")
    private Email email;

    public User(UserId userId, Email email) {
        this.userId = userId;
        this.email = email;
    }

    public UserId getUserId() {
        return userId;
    }

    public Email getEmail() {
        return email;
    }
}
