package io.weba.api.domain.role;

import io.weba.api.domain.user.User;
import io.weba.api.infrastructure.postgres.type.RoleIdType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@TypeDefs({
        @TypeDef(name = "role_id", typeClass = RoleIdType.class)
})
@Table(name = "roles")
@Entity
public class Role implements Serializable {
    @Id
    @Column
    @Basic
    @Type(type = "role_id")
    private RoleId id;

    @Column(name = "name")
    private String name;

    public Role(RoleId id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role(String name) {
        this.id = new RoleId(RoleId.generate());
        this.name = name;
    }

    public Role() {
    }

    public RoleId getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
