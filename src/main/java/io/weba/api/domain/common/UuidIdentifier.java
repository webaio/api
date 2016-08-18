package io.weba.api.domain.common;

import java.util.UUID;

abstract public class UuidIdentifier {
    private final UUID uuid;

    protected UuidIdentifier(UUID uuid) {
        this.uuid = uuid;
    }

    public static UUID generate() {
        return UUID.randomUUID();
    }

    public static UUID fromString(String uuid) {
        return UUID.fromString(uuid);
    }

    public String toString() {
        return this.uuid.toString();
    }
}
