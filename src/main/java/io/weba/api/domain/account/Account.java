package io.weba.api.domain.account;

import io.weba.api.infrastructure.postgres.type.AccountIdType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;

@TypeDefs({
    @TypeDef(name = "account_id", typeClass = AccountIdType.class)
})
@Table(name = "accounts")
@Entity
public class Account implements Serializable {
    @Id
    @Column
    @Basic
    @Type(type = "account_id")
    private AccountId id;

    @Column(name = "name")
    private String name;

    public Account(AccountId id, String name) {
        this.id = id;
        this.name = name;
    }

    public Account(String name) {
        this.id = new AccountId(AccountId.generate());
        this.name = name;
    }

    public Account() {}

    public AccountId getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
