package io.weba.api.domain.role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Table(name = "roles")
@Entity
public class Role implements Serializable {
    @Id
    @Column
    @Basic
    private UUID id;

    @Column(name = "name")
    private String name;

    public Role(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public Role() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
