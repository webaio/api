package io.weba.api.ui.rest.initializer;

import io.weba.api.application.base.DomainEventPublisher;
import io.weba.api.application.event.AddAccountEvent;
import io.weba.api.application.event.AddUserEvent;
import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountRepository;
import io.weba.api.domain.role.Role;
import io.weba.api.domain.role.RoleRepository;
import io.weba.api.domain.user.User;
import io.weba.api.domain.user.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class UserManagementDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final DomainEventPublisher domainEventPublisher;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    private static final String ACCOUNT_UUID = "79d9a686-bff8-4f49-a4f5-6bb00497b7c3";
    private static final String ACCOUNT_NAME = "Default";

    private static final String ROLE_USER_UUID = "e94ade03-f03d-4ba8-91a2-99adf5c6e39b";
    private static final String ROLE_USER_NAME = "ROLE_USER";

    private static final String ROLE_ADMIN_UUID = "5ca1ce75-b336-4232-9641-f7a843920689";
    private static final String ROLE_ADMIN_NAME = "ROLE_ADMIN";

    private static final String ROLE_SUPER_ADMIN_UUID = "1c388058-ad68-49a0-af14-efe26ea0780e";
    private static final String ROLE_SUPER_ADMIN_NAME = "ROLE_SUPER_ADMIN";

    private static final String USER_UUID = "b99d86ef-eead-480c-9b2b-a139635cadc5";

    @Value("${weba.admin.email}")
    public String userEmail;

    @Value("${weba.admin.password}")
    private String userPassword;

    @Value("${weba.admin.first_name}")
    private String userFirstName;

    @Value("${weba.admin.last_name}")
    private String userLastName;

    @Autowired
    public UserManagementDataLoader(
            DomainEventPublisher domainEventPublisher,
            RoleRepository roleRepository,
            AccountRepository accountRepository,
            UserRepository userRepository
    ) {
        this.domainEventPublisher = domainEventPublisher;
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Account defaultAccount = this.accountRepository.findBy(UUID.fromString(ACCOUNT_UUID));

        if (defaultAccount == null) {
            this.createAccount();
        }

        this.createRoles();

        User user = this.userRepository.findBy(UUID.fromString(USER_UUID));


        if (user == null) {
            this.createUser();
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
        Role roleUser = this.roleRepository.findBy(UUID.fromString(ROLE_USER_UUID));
        Role roleAdmin = this.roleRepository.findBy(UUID.fromString(ROLE_ADMIN_UUID));
        Role roleSuperAdmin = this.roleRepository.findBy(UUID.fromString(ROLE_SUPER_ADMIN_UUID));

        if (roleUser == null) {
            this.roleRepository.add(new Role(UUID.fromString(ROLE_USER_UUID), ROLE_USER_NAME));
        }

        if (roleAdmin == null) {
            this.roleRepository.add(new Role(UUID.fromString(ROLE_ADMIN_UUID), ROLE_ADMIN_NAME));
        }

        if (roleSuperAdmin == null) {
            this.roleRepository.add(new Role(UUID.fromString(ROLE_SUPER_ADMIN_UUID), ROLE_SUPER_ADMIN_NAME));
        }
    }

    @Transactional
    private void createUser() {
        AddUserEvent addUserEvent = new AddUserEvent(UUID.fromString(USER_UUID));
        addUserEvent.accountId = UUID.fromString(ACCOUNT_UUID);
        addUserEvent.email = this.userEmail;
        addUserEvent.firstName = this.userFirstName;
        addUserEvent.lastName = this.userLastName;
        addUserEvent.password = this.userPassword;
        addUserEvent.roleId = UUID.fromString(ROLE_SUPER_ADMIN_UUID);

        this.domainEventPublisher.publish(addUserEvent);
    }
}
