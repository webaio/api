package io.weba.api.ui.rest.controller;

import java.security.Principal;

import io.weba.api.domain.user.User;
import io.weba.api.domain.user.UserRepository;
import io.weba.api.domain.user.UserWithUsernameDoesNotExist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @RequestMapping("/user")
    public ResponseEntity<User> user(Principal user) throws UserWithUsernameDoesNotExist {
        return this.userRepository.findBy(user.getName())
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(UserWithUsernameDoesNotExist::new);
    }
}
