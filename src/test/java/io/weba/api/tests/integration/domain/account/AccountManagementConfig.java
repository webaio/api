package io.weba.api.tests.integration.domain.account;

import io.weba.api.domain.account.AccountRepository;
import io.weba.api.infrastructure.domain.account.inmemory.AccountRepositoryImpl;
import org.springframework.context.annotation.Bean;

public class AccountManagementConfig {
    @Bean(name = "accountRepository")
    public AccountRepository getAccountRepository() {
        return new AccountRepositoryImpl();
    }
}
