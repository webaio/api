package io.weba.api.ui.rest.controller;

import io.weba.api.application.base.DomainEventPublisher;
import io.weba.api.application.event.AddAccountEvent;
import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountId;
import io.weba.api.domain.account.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @RequestMapping(method = RequestMethod.POST, value = "/account")
    public ResponseEntity<AccountId> create(@Valid @RequestBody AddAccountEvent addAccountEvent) {
        this.domainEventPublisher.publishEvent(addAccountEvent);

        return new ResponseEntity<>(addAccountEvent.accountId(), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/account/{accountUuid}")
    public ResponseEntity<Account> get(@PathVariable String accountUuid) {
        return Optional.ofNullable(this.accountRepository.findBy(new AccountId(AccountId.fromString(accountUuid))))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
