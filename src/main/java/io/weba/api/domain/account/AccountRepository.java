package io.weba.api.domain.account;

public interface AccountRepository {
    void add(Account account);

    Account findBy(AccountId accountId);
}
