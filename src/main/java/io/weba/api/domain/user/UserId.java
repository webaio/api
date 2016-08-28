package io.weba.api.domain.user;

import io.weba.api.domain.common.UuidIdentifier;

import java.io.Serializable;
import java.util.UUID;

public class UserId extends UuidIdentifier implements Serializable {
    public final UUID id;

    public UserId(UUID uuid) {
        super(uuid);
        this.id = uuid;
    }
}
