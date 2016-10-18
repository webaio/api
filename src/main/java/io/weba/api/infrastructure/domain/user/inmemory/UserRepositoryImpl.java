package io.weba.api.infrastructure.domain.user.inmemory;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.user.User;
import io.weba.api.domain.user.UserRepository;
import io.weba.api.domain.user.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepositoryImpl implements UserRepository {
    private final List<User> list;

    public UserRepositoryImpl() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(User user) {
        this.list.add(user);
    }

    @Override
    public Optional<User> findBy(String username) {
        for (User user : this.list) {
            if (user.getUsername().equals(username)) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findBy(UUID userId) {
        for (User user : this.list) {
            if (user.getId().equals(userId)) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    @Override
    public Users findBy(Account account) {
        Users users = new Users();

//        for (User user : this.list) {
//            if (user.getAccount().getId().equals(account.getId())) {
//                users.add(user);
//            }
//        }

        return users;
    }

    @Override
    public Users findAll() {
        Users users = new Users();

        for (User user : this.list) {
            users.add(user);
        }

        return users;
    }
}
