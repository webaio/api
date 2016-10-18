package io.weba.api.ui.rest.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.weba.api.application.base.DomainEventPublisher;
import io.weba.api.application.event.CreateAccountEvent;
import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountRepository;
import io.weba.api.domain.account.Accounts;
import io.weba.api.ui.rest.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @JsonView(View.AccountCreate.class)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(method = RequestMethod.POST, value = "/admin/account")
    public ResponseEntity<CreateAccountEvent> create(@Valid @RequestBody CreateAccountEvent createAccountEvent) {
        this.domainEventPublisher.publish(createAccountEvent);

        return new ResponseEntity<>(createAccountEvent, HttpStatus.CREATED);
    }

    @JsonView(View.AccountGet.class)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/admin/account/{accountUuid}")
    public ResponseEntity<Account> get(@PathVariable String accountUuid) {
        return this.accountRepository.findBy(UUID.fromString(accountUuid))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @JsonView(View.AccountGetAll.class)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/admin/account")
    public ResponseEntity<Accounts> getAll() {
        return new ResponseEntity<>(this.accountRepository.findAll(), HttpStatus.OK);
    }
}
