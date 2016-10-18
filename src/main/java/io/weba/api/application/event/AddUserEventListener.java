package io.weba.api.application.event;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountWithGivenUuidNotFound;
import io.weba.api.domain.role.RoleWithGivenUuidNotFound;
import io.weba.api.domain.role.RoleRepository;
import io.weba.api.domain.user.User;
import io.weba.api.domain.user.UserRepository;
import io.weba.api.domain.user.UserWithGivenUsernameAlreadyExists;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import java.util.HashSet;

@Component
public class AddUserEventListener {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AddUserEventListener(
            UserRepository userRepository,
            RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
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
                addUserEvent.firstName(),
                addUserEvent.lastName(),
                this.roleRepository.findBy(addUserEvent.role()).orElseThrow(RoleWithGivenUuidNotFound::new)
        );
        user.addAccount(addUserEvent.account);
        this.userRepository.add(user);
    }
}
