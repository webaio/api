package io.weba.api.application.event;

import io.weba.api.domain.account.AccountWithGivenUuidNotFound;
import io.weba.api.domain.account.AccountRepository;
import io.weba.api.domain.role.RoleWithGivenUuidNotFound;
import io.weba.api.domain.role.RoleRepository;
import io.weba.api.domain.user.User;
import io.weba.api.domain.user.UserRepository;
import io.weba.api.domain.user.UserWithGivenUsernameAlreadyExists;
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
    public void handle(AddUserEvent addUserEvent) throws
            AccountWithGivenUuidNotFound, RoleWithGivenUuidNotFound, UserWithGivenUsernameAlreadyExists {
        if (this.userRepository.findBy(addUserEvent.username()).isPresent()) {
            throw new UserWithGivenUsernameAlreadyExists();
        }

        User user = new User(
                addUserEvent.userId(),
                addUserEvent.username(),
                new BCryptPasswordEncoder().encode(addUserEvent.password()),
                addUserEvent.account(),
                addUserEvent.firstName(),
                addUserEvent.lastName(),
                this.roleRepository.findBy(addUserEvent.role()).orElseThrow(RoleWithGivenUuidNotFound::new)
        );
        this.userRepository.add(user);
    }
}
