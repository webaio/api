package io.weba.api.infrastructure.domain.role.postgres;

import io.weba.api.domain.role.Role;
import io.weba.api.domain.role.RoleRepository;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public RoleRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void add(Role role) {
        this.sessionFactory.getCurrentSession().save(role);
    }

    @Override
    @Transactional
    public Optional<Role> findBy(UUID roleId) {
        Object result = this.sessionFactory
                .getCurrentSession()
                .createCriteria(Role.class)
                .add(Restrictions.eq("id", roleId))
                .setMaxResults(1)
                .uniqueResult();

        return Optional.ofNullable((Role) result);
    }

    @Override
    @Transactional
    public Optional<Role> findBy(String name) {
        Object result = this.sessionFactory
                .getCurrentSession()
                .createCriteria(Role.class)
                .add(Restrictions.eq("name", name))
                .setMaxResults(1)
                .uniqueResult();

        return Optional.ofNullable((Role) result);
    }
}
