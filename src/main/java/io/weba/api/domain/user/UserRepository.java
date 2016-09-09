package io.weba.api.domain.user;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void add(User user);

    Optional<User> findBy(String username);

    Optional<User> findBy(UUID userId);
}
