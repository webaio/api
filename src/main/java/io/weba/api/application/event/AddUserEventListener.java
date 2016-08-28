package io.weba.api.application.event;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountRepository;
import io.weba.api.domain.user.User;
import io.weba.api.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AddUserEventListener {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public AddUserEventListener(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @EventListener
    public void handle(AddUserEvent addUserEvent) {
        Account account = this.accountRepository.findBy(addUserEvent.accountId());

        if (account != null) {
            User user = new User(
                    addUserEvent.userId(),
                    addUserEvent.email(),
                    new BCryptPasswordEncoder().encode(addUserEvent.password()),
                    account,
                    addUserEvent.firstName(),
                    addUserEvent.lastName()
            );
            this.userRepository.add(user);
        }
    }
}
