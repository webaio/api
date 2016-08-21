package io.weba.api;

import io.weba.api.domain.account.AccountRepository;
import io.weba.api.infrastructure.domain.account.inmemory.AccountRepositoryImpl;
import org.springframework.context.annotation.Bean;

public class TestDomainContextConfig {
    @Bean(name = "accountRepository")
    public AccountRepository getAccountRepository() {
        return new AccountRepositoryImpl();
    }
}
