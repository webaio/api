package io.weba.api.infrastructure.domain.account.inmemory;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountId;
import io.weba.api.domain.account.AccountRepository;

import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {
    private final List<Account> list;

    public AccountRepositoryImpl() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(Account account) {
        list.add(account);
    }

    @Override
    public Account findBy(AccountId accountId) {
        for (Account account : list) {
            if (account.getId() == accountId) {
                return account;
            }
        }

        return null;
    }
}
