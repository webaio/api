package io.weba.api.domain.role;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository {
    void add(Role role);

    Optional<Role> findBy(UUID roleId);
}
