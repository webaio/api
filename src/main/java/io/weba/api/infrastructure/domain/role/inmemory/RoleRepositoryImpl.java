package io.weba.api.infrastructure.domain.role.inmemory;

import io.weba.api.domain.role.Role;
import io.weba.api.domain.role.RoleRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RoleRepositoryImpl implements RoleRepository {
    private final List<Role> list;

    public RoleRepositoryImpl() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(Role role) {
        this.list.add(role);
    }

    @Override
    public Optional<Role> findBy(UUID roleId) {
        for (Role role : this.list) {
            if (role.getId() == roleId) {
                return Optional.of(role);
            }
        }

        return Optional.empty();
    }
}
