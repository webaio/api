package io.weba.api.domain.user;

import java.util.UUID;

public interface UserRepository {
    void add(User user);

    User findBy(String email);

    User findBy(UUID userId);
}
