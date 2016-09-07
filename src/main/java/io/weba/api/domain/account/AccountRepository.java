package io.weba.api.domain.account;

import java.util.UUID;

public interface AccountRepository {
    void add(Account account);

    Account findBy(UUID accountId);
}
