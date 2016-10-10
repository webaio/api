package io.weba.api.ui.rest.initializer;

import io.weba.api.application.base.DomainEventPublisher;
import io.weba.api.application.event.AddAccountEvent;
import io.weba.api.application.event.AddOauthClientDetailsEvent;
import io.weba.api.application.event.AddUserEvent;
import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountRepository;
import io.weba.api.domain.oauth.OauthClientDetailsRepository;
import io.weba.api.domain.role.Role;
import io.weba.api.domain.role.RoleRepository;
import io.weba.api.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@Profile({"development", "testing"})
public class UserManagementDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final DomainEventPublisher domainEventPublisher;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final OauthClientDetailsRepository oauthClientDetailsRepository;

    public static final UUID ACCOUNT_UUID = UUID.fromString("79d9a686-bff8-4f49-a4f5-6bb00497b7c3");
    public static final String ACCOUNT_NAME = "Weba";

    public static final UUID ROLE_USER_UUID = UUID.fromString("e94ade03-f03d-4ba8-91a2-99adf5c6e39b");
    public static final UUID ROLE_ADMIN_UUID = UUID.fromString("5ca1ce75-b336-4232-9641-f7a843920689");
    public static final UUID ROLE_SUPER_ADMIN_UUID = UUID.fromString("1c388058-ad68-49a0-af14-efe26ea0780e");

    public static final UUID USER_USER_UUID = UUID.fromString("6e781882-1617-4b85-9f7a-d455298debc4");
    public static final UUID USER_ADMIN_UUID = UUID.fromString("4aae5651-3dce-420b-a077-893fcfc5548b");
    public static final UUID USER_SUPER_ADMIN_UUID = UUID.fromString("b18e510d-9956-4707-86f7-663d631e01a9");

    public static final UUID OAUTH_CLIENT_UUID = UUID.fromString("f1b1bde8-ccd5-46da-b1ab-8c8eb194d57d");
    public static final UUID OAUTH_SECRET_UUID = UUID.fromString("ee29fd21-8852-496f-89ef-f3c6aea4a75c");

    @Autowired
    public UserManagementDataLoader(
            DomainEventPublisher domainEventPublisher,
            RoleRepository roleRepository,
            AccountRepository accountRepository,
            UserRepository userRepository,
            OauthClientDetailsRepository oauthClientDetailsRepository
    ) {
        this.domainEventPublisher = domainEventPublisher;
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.oauthClientDetailsRepository = oauthClientDetailsRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Optional<Account> account = this.createAccount();

        this.createRoles();
        this.createUser(USER_USER_UUID, account.get(), Role.ROLE_USER, "user@weba.io", "Marco", "Wayne");
        this.createUser(USER_ADMIN_UUID, account.get(), Role.ROLE_ADMIN, "admin@weba.io", "Tommy", "Lee");
        this.createUser(USER_SUPER_ADMIN_UUID, account.get(), Role.ROLE_SUPER_ADMIN, "super_admin@weba.io", "Jessica", "Kovalski");

        boolean isClientExists = this.oauthClientDetailsRepository.findBy(OAUTH_CLIENT_UUID.toString()).isPresent();

        if (!isClientExists) {
            this.createOauthClient();
        }
    }

    @Transactional
    private Optional<Account> createAccount() {
        boolean present = this
                .accountRepository
                .findBy(ACCOUNT_UUID)
                .isPresent();

        if(!present) {
            AddAccountEvent addAccountEvent = new AddAccountEvent(ACCOUNT_UUID);
            addAccountEvent.name = ACCOUNT_NAME;
            this.domainEventPublisher.publish(addAccountEvent);
        }

        return this.accountRepository.findBy(ACCOUNT_UUID);
    }

    @Transactional
    private void createRoles() {
        boolean isRoleUserExists = this.roleRepository.findBy(ROLE_USER_UUID).isPresent();
        boolean isRoleUserAdminExists = this.roleRepository.findBy(ROLE_ADMIN_UUID).isPresent();
        boolean isRoleUserSuperAdminExists = this.roleRepository.findBy(ROLE_SUPER_ADMIN_UUID).isPresent();

        if (!isRoleUserExists) {
            this.roleRepository.add(new Role(ROLE_USER_UUID, Role.ROLE_USER));
        }

        if (!isRoleUserAdminExists) {
            this.roleRepository.add(new Role(ROLE_ADMIN_UUID, Role.ROLE_ADMIN));
        }

        if (!isRoleUserSuperAdminExists) {
            this.roleRepository.add(new Role(ROLE_SUPER_ADMIN_UUID, Role.ROLE_SUPER_ADMIN));
        }
    }

    @Transactional
    private void createUser(UUID userId, Account account, String role, String username, String firstName, String lastName) {
        boolean isUserExists = this.userRepository.findBy(userId).isPresent();
        if (!isUserExists) {
            AddUserEvent addUserEvent = new AddUserEvent(userId);
            addUserEvent.account = account;
            addUserEvent.username = username;
            addUserEvent.firstName = firstName;
            addUserEvent.lastName = lastName;
            addUserEvent.password = "test";
            addUserEvent.role = role;

            this.domainEventPublisher.publish(addUserEvent);
        }
    }

    @Transactional
    private void createOauthClient() {
        AddOauthClientDetailsEvent addOauthClientDetailsEvent = new AddOauthClientDetailsEvent(
                OAUTH_CLIENT_UUID,
                OAUTH_SECRET_UUID
        );

        this.domainEventPublisher.publish(addOauthClientDetailsEvent);
    }
}
