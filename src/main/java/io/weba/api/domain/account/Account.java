package io.weba.api.domain.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Table(name = "accounts")
@Entity
public class Account implements Serializable {
    @Id
    @Column
    private UUID id;

    @Column(name = "name")
    private String name;

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
}
