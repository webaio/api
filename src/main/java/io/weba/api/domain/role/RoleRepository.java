package io.weba.api.domain.role;

import java.util.UUID;

public interface RoleRepository {
    void add(Role role);

    Role findBy(UUID roleId);
}
