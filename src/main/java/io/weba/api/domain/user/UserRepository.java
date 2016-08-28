package io.weba.api.domain.user;

public interface UserRepository {
    void add(User user);

    User findBy(String email);

    User findBy(UserId userId);
}
