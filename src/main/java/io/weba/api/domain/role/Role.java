package io.weba.api.domain.role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Table(name = "roles")
@Entity
public class Role implements Serializable {
    public static String ROLE_USER = "ROLE_USER";
    public static String ROLE_ADMIN = "ROLE_ADMIN";
    public static String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";

    @Id
    @Column
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
