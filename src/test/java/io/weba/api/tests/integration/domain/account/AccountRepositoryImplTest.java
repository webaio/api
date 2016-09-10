package io.weba.api.tests.integration.domain.account;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountRepository;
import io.weba.api.rest.application.SpringApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Assert;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.UUID;

@ContextConfiguration(classes = {SpringApplication.class}, loader = SpringBootContextLoader.class)
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountRepositoryImplTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void itPersistsAccountInStorage() {
        UUID accountId = UUID.randomUUID();
        Account account = new Account(accountId, "Test account repository");
        this.accountRepository.add(account);
        Account persistedAccount = this.accountRepository.findBy(accountId);

        Assert.assertEquals(persistedAccount.getId(), account.getId());
    }
}
