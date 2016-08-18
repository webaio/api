package io.weba.api.ui.rest.controller;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountId;
import io.weba.api.domain.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/")
    public String get() {
        this.accountRepository.createNewAccount(new Account("test"));
        return "Hello World2!";
    }

    @RequestMapping("/test/{test}")

    public Account test(@PathVariable String test) {
        Account by = this.accountRepository.findBy(new AccountId(AccountId.fromString(test)));

        return by;
    }
}
