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
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("development")
public class UserManagementDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final DomainEventPublisher domainEventPublisher;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final OauthClientDetailsRepository oauthClientDetailsRepository;

    private static final String ACCOUNT_UUID = "79d9a686-bff8-4f49-a4f5-6bb00497b7c3";
    private static final String ACCOUNT_NAME = "Default";

    private static final String ROLE_USER_UUID = "e94ade03-f03d-4ba8-91a2-99adf5c6e39b";

    private static final String ROLE_ADMIN_UUID = "5ca1ce75-b336-4232-9641-f7a843920689";

    private static final String ROLE_SUPER_ADMIN_UUID = "1c388058-ad68-49a0-af14-efe26ea0780e";

    private static final String USER_UUID = "b99d86ef-eead-480c-9b2b-a139635cadc5";

    @Value("${weba.admin.username}")
    public String userEmail;

    @Value("${weba.admin.password}")
    private String userPassword;

    @Value("${weba.admin.first_name}")
    private String userFirstName;

    @Value("${weba.admin.last_name}")
    private String userLastName;

    @Value("${weba.admin.client_id}")
    private String clientId;

    @Value("${weba.admin.client_secret}")
    private String clientSecret;

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

        boolean present = this
                .accountRepository
                .findBy(UUID.fromString(ACCOUNT_UUID))
                .isPresent();

        if (!present) {
            this.createAccount();
        }

        this.createRoles();

        boolean isUserExists = this.userRepository.findBy(UUID.fromString(USER_UUID)).isPresent();

        if (!isUserExists) {
            this.createUser();
        }

        boolean isClientExists = this.oauthClientDetailsRepository.findBy(this.clientId).isPresent();

        if (!isClientExists) {
            this.createOauthClient();
        }
    }

    @Transactional
    private void createAccount() {
        AddAccountEvent addAccountEvent = new AddAccountEvent(UUID.fromString(ACCOUNT_UUID));
        addAccountEvent.name = ACCOUNT_NAME;
        this.domainEventPublisher.publish(addAccountEvent);
    }

    @Transactional
    private void createRoles() {
        boolean isRoleUserExists = this.roleRepository.findBy(UUID.fromString(ROLE_USER_UUID)).isPresent();
        boolean isRoleUserAdminExists =this.roleRepository.findBy(UUID.fromString(ROLE_ADMIN_UUID)).isPresent();
        boolean isRoleUserSuperAdminExists =this.roleRepository.findBy(UUID.fromString(ROLE_SUPER_ADMIN_UUID)).isPresent();

        if (!isRoleUserExists) {
            this.roleRepository.add(new Role(UUID.fromString(ROLE_USER_UUID), Role.ROLE_USER));
        }

        if (!isRoleUserAdminExists) {
            this.roleRepository.add(new Role(UUID.fromString(ROLE_ADMIN_UUID), Role.ROLE_ADMIN));
        }

        if (!isRoleUserSuperAdminExists) {
            this.roleRepository.add(new Role(UUID.fromString(ROLE_SUPER_ADMIN_UUID), Role.ROLE_SUPER_ADMIN));
        }
    }

    @Transactional
    private void createUser() {
        AddUserEvent addUserEvent = new AddUserEvent(UUID.fromString(USER_UUID));
        addUserEvent.account = this.accountRepository.findBy(UUID.fromString(ACCOUNT_UUID)).get();
        addUserEvent.username = this.userEmail;
        addUserEvent.firstName = this.userFirstName;
        addUserEvent.lastName = this.userLastName;
        addUserEvent.password = this.userPassword;
        addUserEvent.role = ROLE_SUPER_ADMIN_UUID;

        this.domainEventPublisher.publish(addUserEvent);
    }

    @Transactional
    private void createOauthClient() {
        AddOauthClientDetailsEvent addOauthClientDetailsEvent = new AddOauthClientDetailsEvent(
                UUID.fromString(this.clientId),
                UUID.fromString(this.clientSecret)
        );

        this.domainEventPublisher.publish(addOauthClientDetailsEvent);
    }
}
