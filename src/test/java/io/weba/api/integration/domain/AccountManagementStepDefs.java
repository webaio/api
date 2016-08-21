package io.weba.api.integration.domain;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.weba.api.TestDomainContextConfig;
import io.weba.api.application.base.DomainEventPublisher;
import io.weba.api.application.event.AddAccountEvent;
import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountRepository;
import io.weba.api.ui.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(classes = {Application.class, TestDomainContextConfig.class}, loader = SpringBootContextLoader.class)
@WebAppConfiguration
public class AccountManagementStepDefs {
    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    @Given("^configured account with name \"(.*?)\"$")
    public void configured_account_with_name(String accountName) throws Throwable {
        AddAccountEvent addAccountEvent = new AddAccountEvent();
        addAccountEvent.name = accountName;
        this.domainEventPublisher.publishEvent(addAccountEvent);
        this.account = this.accountRepository.findBy(addAccountEvent.accountId());
    }

    @Given("^logged as administrator$")
    public void logged_as_administrator() throws Throwable {

    }

    @When("^I decide to create account with configuration:$")
    public void i_decide_to_create_account_with_configuration(DataTable arg1) throws Throwable {

    }

    @Then("^I should see configured tracker with given javascript code$")
    public void i_should_see_configured_tracker_with_given_javascript_code() throws Throwable {

    }
}
