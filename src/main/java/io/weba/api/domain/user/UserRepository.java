package io.weba.api.domain.user;

import io.weba.api.domain.account.Account;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void add(User user);

    Optional<User> findBy(String username);

    Optional<User> findBy(UUID userId);

    Users findBy(Account account);

    Users findAll();
}
