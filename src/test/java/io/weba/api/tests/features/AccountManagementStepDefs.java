package io.weba.api.tests.features;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.weba.api.application.base.DomainEventPublisher;
import io.weba.api.application.event.AddAccountEvent;
import io.weba.api.application.event.AddNewSiteEvent;
import io.weba.api.application.event.AddUserEvent;
import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountRepository;
import io.weba.api.domain.role.Role;
import io.weba.api.domain.role.RoleRepository;
import io.weba.api.domain.site.Site;
import io.weba.api.domain.site.SiteRepository;
import io.weba.api.domain.user.User;
import io.weba.api.domain.user.UserRepository;
import io.weba.api.domain.user.Users;
import io.weba.api.ui.rest.application.SpringApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(classes = {SpringApplication.class, AccountManagementConfig.class}, loader = SpringBootContextLoader.class)
@WebAppConfiguration
public class AccountManagementStepDefs {
    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SiteRepository siteRepository;

    private Account account;

    private User user;

    private Site site;

    @Given("^configured account with name \"(.*?)\"$")
    public void configured_account_with_name(String accountName) throws Throwable {
        AddAccountEvent addAccountEvent = new AddAccountEvent();
        addAccountEvent.name = accountName;
        this.domainEventPublisher.publish(addAccountEvent);
        this.account = this.accountRepository.findBy(addAccountEvent.accountId()).get();
    }

    @And("^logged as an user with admin role$")
    public void loggedAsAnUserWithAdminRole() throws Throwable {
        UUID roleId = UUID.randomUUID();
        this.roleRepository.add(new Role(roleId, Role.ROLE_ADMIN));

        AddUserEvent addUserEvent = new AddUserEvent();
        addUserEvent.account = this.account;
        addUserEvent.firstName = "John";
        addUserEvent.lastName = "Doe";
        addUserEvent.username = "admin@weba.io";
        addUserEvent.password = "test";
        addUserEvent.role = Role.ROLE_ADMIN;

        this.domainEventPublisher.publish(addUserEvent);
        this.user = this.userRepository.findBy("admin@weba.io").get();
    }

    @When("^I decide to create site with configuration$")
    public void iDecideToCreateSiteWithConfiguration(List<String> entries) throws Throwable {
        AddNewSiteEvent addNewSiteEvent = new AddNewSiteEvent();
        addNewSiteEvent.account = this.user.getAccount();
        addNewSiteEvent.name = entries.get(0);
        addNewSiteEvent.url = entries.get(1);
        addNewSiteEvent.timezone = entries.get(2);
        this.domainEventPublisher.publish(addNewSiteEvent);
        this.site = this.siteRepository.findBy(addNewSiteEvent.siteId(), this.user.getAccount()).get();
    }

    @Then("^I should see configured tracker with given javascript code$")
    public void iShouldSeeConfiguredTrackerWithGivenJavascriptCode() throws Throwable {
        boolean matches = this.site.getTracker().getTrackerContent().matches("<script\\b[^>]*>([\\s\\S]*?)<\\/script>");
        assertTrue(matches);
    }

    @When("^I decide to create new account with read only role$")
    public void iDecideToCreateNewAccountWithReadOnlyRole(List<String> entries) throws Throwable {
        UUID roleId = UUID.randomUUID();
        this.roleRepository.add(new Role(roleId, Role.ROLE_USER));

        AddUserEvent addUserEvent = new AddUserEvent();
        addUserEvent.account = this.user.getAccount();
        addUserEvent.firstName = entries.get(2);
        addUserEvent.lastName = entries.get(3);
        addUserEvent.username = entries.get(0);
        addUserEvent.password = entries.get(1);
        addUserEvent.role = Role.ROLE_USER;
        this.domainEventPublisher.publish(addUserEvent);
        User user = this.userRepository.findBy(entries.get(0)).get();

        assertEquals(entries.get(0), user.getUsername());
    }

    @Then("^I should see create user with username \"([^\"]*)\"$")
    public void iShouldSeeCreateUserWithUsername(String username) throws Throwable {
        Boolean isUserExisting = false;
        Users userForAccount = this.userRepository.findBy(this.user.getAccount());

        for (User user : userForAccount) {
            if (user.getUsername().equals(username)) {
                isUserExisting = true;
            }
        }

        assertTrue(isUserExisting);
    }

    @When("^I promote user \"([^\"]*)\" to role admin$")
    public void iPromoteUserToRoleAdmin(String arg0) throws Throwable {
    }

    @Then("^I should see user \"([^\"]*)\" as a admin$")
    public void iShouldSeeUserAsAAdmin(String arg0) throws Throwable {
    }
}
