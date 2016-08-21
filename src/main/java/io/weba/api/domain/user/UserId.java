package io.weba.api.domain.user;

import io.weba.api.domain.common.UuidIdentifier;
import java.util.UUID;

public class UserId extends UuidIdentifier{
    protected UserId(UUID uuid) {
        super(uuid);
    }
}
