package io.weba.api.domain.role;

public interface RoleRepository {
    void add(Role role);

    Role findBy(RoleId roleId);
}
