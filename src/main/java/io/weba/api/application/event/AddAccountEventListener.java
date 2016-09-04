package io.weba.api.application.event;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AddAccountEventListener {
    private final AccountRepository accountRepository;

    @Autowired
    public AddAccountEventListener(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @EventListener
    public void handle(AddAccountEvent addAccountEvent) {
        Account account = new Account(addAccountEvent.accountId(), addAccountEvent.name());
        this.accountRepository.add(account);
    }
}
