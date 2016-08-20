package io.weba.api.application.event;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class AddAccountEventListener {
    @Autowired
    private AccountRepository accountRepository;

    @EventListener
    @TransactionalEventListener
    public void handle(AddAccountEvent addAccountEvent) {
        Account account = new Account(addAccountEvent.accountId(), addAccountEvent.name());
        this.accountRepository.createNewAccount(account);
    }
}
