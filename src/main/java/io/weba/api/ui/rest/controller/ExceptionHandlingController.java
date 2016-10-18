package io.weba.api.ui.rest.controller;

import io.weba.api.domain.account.AccountWithGivenNameAlreadyExists;
import io.weba.api.domain.account.AccountWithGivenUuidNotFound;
import io.weba.api.domain.role.RoleWithGivenUuidNotFound;
import io.weba.api.domain.site.SiteWithGivenNameAlreadyExistsForAccount;
import io.weba.api.domain.user.UserWithGivenUsernameAlreadyExists;
import io.weba.api.domain.user.UserWithGivenUsernameDoesNotExist;
import io.weba.api.domain.user.UserWithGivenUuidDoesNotExist;
import io.weba.api.ui.rest.GenericExceptionDecorator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlingController {
    @ExceptionHandler(AccountWithGivenNameAlreadyExists.class)
    public ResponseEntity<GenericExceptionDecorator> handleAccountWithGivenNameAlreadyExists() {
        return new ResponseEntity<>(
                new GenericExceptionDecorator("Account with given name already exists"), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(AccountWithGivenUuidNotFound.class)
    public ResponseEntity<GenericExceptionDecorator> handleAccountWithGivenUuidNotFound() {
        return new ResponseEntity<>(
                new GenericExceptionDecorator("Account with given uuid is not found"), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(RoleWithGivenUuidNotFound.class)
    public ResponseEntity<GenericExceptionDecorator> handleRoleWithGivenUuidNotFound() {
        return new ResponseEntity<>(
                new GenericExceptionDecorator("Role with given uuid is not found"), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UserWithGivenUsernameAlreadyExists.class)
    public ResponseEntity<GenericExceptionDecorator> handleUserWithGivenUsernameAlreadyExists() {
        return new ResponseEntity<>(
                new GenericExceptionDecorator("User with given username already exists"), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UserWithGivenUsernameDoesNotExist.class)
    public ResponseEntity<GenericExceptionDecorator> handleUserWithGivenUsernameDoesNotExist() {
        return new ResponseEntity<>(
                new GenericExceptionDecorator("User with given username does not exist"), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UserWithGivenUuidDoesNotExist.class)
    public ResponseEntity<GenericExceptionDecorator> handleUserWithGivenUuidDoesNotExist() {
        return new ResponseEntity<>(
                new GenericExceptionDecorator("User with given uuid does not exist"), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(SiteWithGivenNameAlreadyExistsForAccount.class)
    public ResponseEntity<GenericExceptionDecorator> handleSiteWithGivenNameAlreadyExistsForAccount() {
        return new ResponseEntity<>(
                new GenericExceptionDecorator("Site with given name already exists for account"), HttpStatus.BAD_REQUEST
        );
    }
}
