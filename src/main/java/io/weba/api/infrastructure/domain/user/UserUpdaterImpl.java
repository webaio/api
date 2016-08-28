package io.weba.api.infrastructure.domain.user;

import io.weba.api.domain.user.User;
import io.weba.api.domain.user.UserUpdater;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserUpdaterImpl implements UserUpdater {
    private final SessionFactory sessionFactory;

    @Autowired
    public UserUpdaterImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void update(User user) {
        this.sessionFactory.getCurrentSession().merge(user);
        this.sessionFactory.getCurrentSession().saveOrUpdate(user);
    }
}
