package io.weba.api.domain.role;

import io.weba.api.domain.common.UuidIdentifier;

import java.io.Serializable;
import java.util.UUID;

public class RoleId extends UuidIdentifier implements Serializable {
    public final UUID id;

    public RoleId(UUID uuid) {
        super(uuid);
        this.id = uuid;
    }
}
