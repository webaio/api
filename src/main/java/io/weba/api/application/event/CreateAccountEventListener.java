package io.weba.api.application.event;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountWithGivenNameAlreadyExists;
import io.weba.api.domain.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public final class CreateAccountEventListener {
    private final AccountRepository accountRepository;

    @Autowired
    public CreateAccountEventListener(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @EventListener
    public void handle(CreateAccountEvent createAccountEvent) throws AccountWithGivenNameAlreadyExists {
        if (this.accountRepository.findBy(createAccountEvent.name()).isPresent()) {
            throw new AccountWithGivenNameAlreadyExists();
        }

        this.accountRepository.add(new Account(createAccountEvent.accountId(), createAccountEvent.name()));
    }
}
