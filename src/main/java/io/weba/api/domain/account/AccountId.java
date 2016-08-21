package io.weba.api.domain.account;

import io.weba.api.domain.common.UuidIdentifier;

import java.io.Serializable;
import java.util.UUID;

public class AccountId extends UuidIdentifier implements Serializable {
    public final UUID id;

    public AccountId(UUID uuid) {
        super(uuid);
        this.id = uuid;
    }

    @Override
    public boolean equals(Object obj) {
        if ((null == obj) || (obj.getClass() != AccountId.class)) {
            return false;
        }

        AccountId id = (AccountId) obj;

        return id.id == this.id;
    }
}
