package io.weba.api.infrastructure.domain.user;

import io.weba.api.domain.user.User;
import io.weba.api.domain.user.UserId;
import io.weba.api.domain.user.UserRepository;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void add(User user) {
        this.sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @Transactional
    public User findBy(String email) {
        Object result = this.sessionFactory
                .getCurrentSession()
                .createCriteria(User.class)
                .add(Restrictions.eq("email", email))
                .setMaxResults(1)
                .uniqueResult();

        return (User) result;
    }

    @Override
    @Transactional
    public User findBy(UserId userId) {
        Object result = this.sessionFactory
                .getCurrentSession()
                .createCriteria(User.class)
                .add(Restrictions.eq("id", userId))
                .setMaxResults(1)
                .uniqueResult();

        return (User) result;
    }
}
