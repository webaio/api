package io.weba.api.domain.account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
    void add(Account account);

    Optional<Account> findBy(UUID accountId);

    Optional<Account> findBy(String name);

    Accounts findAll();
}
