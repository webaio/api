package io.weba.api.integration.repository;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountId;
import io.weba.api.domain.account.AccountRepository;
import io.weba.api.ui.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@ContextConfiguration(classes = {Application.class}, loader = SpringBootContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountRepositoryImplTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void itPersistsAccountInStorage() {
        AccountId accountId = new AccountId(AccountId.generate());
        Account account = new Account(accountId, "Test account repository");
        this.accountRepository.createNewAccount(account);
        Account persistedAccount = this.accountRepository.findBy(accountId);

        assertEquals(persistedAccount.getId().id, account.getId().id);
    }
}
