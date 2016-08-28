package io.weba.api.ui.rest.security.user;

import io.weba.api.domain.user.User;
import io.weba.api.domain.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findBy(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        return new UserDetailsAdapter(user);
    }
}
