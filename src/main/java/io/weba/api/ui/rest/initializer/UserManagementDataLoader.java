package io.weba.api.ui.rest.initializer;

import io.weba.api.application.base.DomainEventPublisher;
import io.weba.api.application.event.AddAccountEvent;
import io.weba.api.application.event.AddUserEvent;
import io.weba.api.application.event.AssignUserToRoleEvent;
import io.weba.api.domain.account.AccountId;
import io.weba.api.domain.role.Role;
import io.weba.api.domain.role.RoleId;
import io.weba.api.domain.role.RoleRepository;
import io.weba.api.domain.user.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class UserManagementDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final DomainEventPublisher domainEventPublisher;
    private final RoleRepository roleRepository;

    private static final String ACCOUNT_UUID = "79d9a686-bff8-4f49-a4f5-6bb00497b7c3";
    private static final String ACCOUNT_NAME = "Default";

    private static final String ROLE_USER_UUID = "e94ade03-f03d-4ba8-91a2-99adf5c6e39b";
    private static final String ROLE_USER_NAME = "ROLE_USER";

    private static final String ROLE_ADMIN_UUID = "5ca1ce75-b336-4232-9641-f7a843920689";
    private static final String ROLE_ADMIN_NAME = "ROLE_ADMIN";

    private static final String ROLE_SUPER_ADMIN_UUID = "1c388058-ad68-49a0-af14-efe26ea0780e";
    private static final String ROLE_SUPER_ADMIN_NAME = "ROLE_SUPER_ADMIN";

    private static final String USER_UUID = "b99d86ef-eead-480c-9b2b-a139635cadc5";
    private static final String USER_EMAIL = "admin@weba.io";
    private static final String USER_PASSWORD = "!weba007";
    private static final String USER_FIRST_NAME = "John";
    private static final String USER_LAST_NAME = "Doe";

    @Autowired
    public UserManagementDataLoader(
            DomainEventPublisher domainEventPublisher,
            RoleRepository roleRepository
    ) {
        this.domainEventPublisher = domainEventPublisher;
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.createAccount();
        this.createRoles();
        this.createUser();
        this.createUserRole();
    }

    private void createAccount() {
        AddAccountEvent addAccountEvent = new AddAccountEvent(
                new AccountId(AccountId.fromString(ACCOUNT_UUID))
        );
        addAccountEvent.name = ACCOUNT_NAME;
        this.domainEventPublisher.publishEvent(addAccountEvent);
    }

    private void createRoles() {
        this.roleRepository.add(new Role(new RoleId(RoleId.fromString(ROLE_USER_UUID)), ROLE_USER_NAME));
        this.roleRepository.add(new Role(new RoleId(RoleId.fromString(ROLE_ADMIN_UUID)), ROLE_ADMIN_NAME));
        this.roleRepository.add(new Role(new RoleId(RoleId.fromString(ROLE_SUPER_ADMIN_UUID)), ROLE_SUPER_ADMIN_NAME));
    }

    private void createUser() {
        AddUserEvent addUserEvent = new AddUserEvent(new UserId(UserId.fromString(USER_UUID)));
        addUserEvent.accountId = new AccountId(AccountId.fromString(ACCOUNT_UUID));
        addUserEvent.email = USER_EMAIL;
        addUserEvent.firstName = USER_FIRST_NAME;
        addUserEvent.lastName = USER_LAST_NAME;
        addUserEvent.password = USER_PASSWORD;

        this.domainEventPublisher.publishEvent(addUserEvent);
    }

    private void createUserRole() {
        AssignUserToRoleEvent assignUserToRoleEvent = new AssignUserToRoleEvent();
        assignUserToRoleEvent.userId = new UserId(UserId.fromString(USER_UUID));
        assignUserToRoleEvent.roleId = new RoleId(RoleId.fromString(ROLE_SUPER_ADMIN_UUID));

        this.domainEventPublisher.publishEvent(assignUserToRoleEvent);
    }
}
