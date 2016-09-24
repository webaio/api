package io.weba.api.tests.integration.domain.account;

import java.util.UUID;
import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountRepository;
import io.weba.api.ui.rest.config.Application;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(classes = {Application.class}, loader = SpringBootContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class AccountRepositoryImplTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void itPersistsAccountInStorage() {
        UUID accountId = UUID.randomUUID();
        Account account = new Account(accountId, "Test account repository");
        this.accountRepository.add(account);
        Account persistedAccount = this.accountRepository.findBy(accountId).map(
                result -> {

                    assertEquals(persistedAccount.getId(), account.getId());

                    return result;
                }
        ).orElse(result -> {
            return result;
        });


    }
}
