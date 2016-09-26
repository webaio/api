package io.weba.api.application.event;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountForGivenIdNotFound;
import io.weba.api.domain.account.AccountRepository;
import io.weba.api.domain.role.Role;
import io.weba.api.domain.role.RoleForGivenIdNotFound;
import io.weba.api.domain.role.RoleRepository;
import io.weba.api.domain.user.User;
import io.weba.api.domain.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

@Component
public class AddUserEventListener {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AddUserEventListener(
            UserRepository userRepository,
            AccountRepository accountRepository,
            RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }

    @EventListener
    public void handle(AddUserEvent addUserEvent) throws AccountForGivenIdNotFound, RoleForGivenIdNotFound {
        Role role = this
                .roleRepository
                .findBy(addUserEvent.roleId())
                .orElseThrow(RoleForGivenIdNotFound::new);
        Account account = this
                .accountRepository
                .findBy(addUserEvent.accountId())
                .orElseThrow(AccountForGivenIdNotFound::new);

        User user = new User(
                addUserEvent.userId(),
                addUserEvent.username(),
                new BCryptPasswordEncoder().encode(addUserEvent.password()),
                account,
                addUserEvent.firstName(),
                addUserEvent.lastName(),
                role
        );
        this.userRepository.add(user);
    }
}
