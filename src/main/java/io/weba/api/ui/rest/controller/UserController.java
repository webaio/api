package io.weba.api.ui.rest.controller;

import java.security.Principal;
import java.util.UUID;
import io.weba.api.application.base.DomainEventPublisher;
import io.weba.api.application.event.AddUserEvent;
import io.weba.api.application.event.EditUserEvent;
import io.weba.api.domain.account.AccountWithGivenUuidNotFound;
import io.weba.api.domain.account.AccountRepository;
import io.weba.api.domain.role.Role;
import io.weba.api.domain.user.User;
import io.weba.api.domain.user.UserRepository;
import io.weba.api.domain.user.UserWithUsernameDoesNotExist;
import io.weba.api.domain.user.Users;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/user/me")
    public ResponseEntity<User> user(Principal user) throws UserWithUsernameDoesNotExist {
        return this
                .userRepository.findBy(user.getName())
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(UserWithUsernameDoesNotExist::new);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public ResponseEntity<Users> getListForAccount(Principal principal) throws UserWithUsernameDoesNotExist {
        return this
                .userRepository
                .findBy(principal.getName())
                .map(user -> new ResponseEntity<>(this.userRepository.findBy(user.getAccount()), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/admin/user/{accountUuid}")
    public ResponseEntity<Users> getAdminListForAccount(@PathVariable String accountUuid)
            throws AccountWithGivenUuidNotFound {
        return this
                .accountRepository
                .findBy(UUID.fromString(accountUuid))
                .map(account -> new ResponseEntity<>(this.userRepository.findBy(account), HttpStatus.OK))
                .orElseThrow(AccountWithGivenUuidNotFound::new);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    @RequestMapping(method = RequestMethod.POST, value = "/user")
    public ResponseEntity<AddUserEvent> create(@Valid @RequestBody AddUserEvent addUserEvent, Principal principal)
            throws UserWithUsernameDoesNotExist {
        addUserEvent.role = Role.ROLE_USER;
        addUserEvent.account = this
                .userRepository
                .findBy(principal.getName())
                .orElseThrow(UserWithUsernameDoesNotExist::new)
                .getAccount();
        this.domainEventPublisher.publish(addUserEvent);

        return new ResponseEntity<>(addUserEvent, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    @RequestMapping(method = RequestMethod.PUT, value = "/user/{userUuid}")
    public ResponseEntity edit(@PathVariable String userUuid, @Valid @RequestBody EditUserEvent editUserEvent) {
        editUserEvent.userId = UUID.fromString(userUuid);

        return new ResponseEntity<>(editUserEvent, HttpStatus.NO_CONTENT);
    }
}
