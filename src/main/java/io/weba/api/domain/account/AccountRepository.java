package io.weba.api.domain.account;

public interface AccountRepository {
    void createNewAccount(Account account);

    Account findBy(AccountId accountId);
}
