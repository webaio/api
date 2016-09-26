package io.weba.api.infrastructure.domain.account.inmemory;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public Account findBy(UUID accountId) {
        for (Account account : list) {
            if (account.getId() == accountId) {
                return account;
            }
        }

        return null;
    }
}
