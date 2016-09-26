package io.weba.api.ui.rest.controller;

import java.security.Principal;
import io.weba.api.domain.user.User;
import io.weba.api.domain.user.UserRepository;
import io.weba.api.domain.user.UserWithUsernameDoesNotExist;
import io.weba.api.domain.user.Users;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/user/me")
    public ResponseEntity<User> user(Principal user) throws UserWithUsernameDoesNotExist {
        return this.userRepository.findBy(user.getName())
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(UserWithUsernameDoesNotExist::new);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public ResponseEntity<Users> getListForAccount(Principal principal) throws UserWithUsernameDoesNotExist {
        User user = this.userRepository.findBy(principal.getName()).orElseThrow(UserWithUsernameDoesNotExist::new);
        Users users = this.userRepository.findBy(user.getAccount());

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/admin/user")
    public ResponseEntity<Users> getAll() {
        Users users = this.userRepository.findAll();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
