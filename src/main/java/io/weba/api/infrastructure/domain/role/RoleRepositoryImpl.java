package io.weba.api.infrastructure.domain.role;

import io.weba.api.domain.role.Role;
import io.weba.api.domain.role.RoleId;
import io.weba.api.domain.role.RoleRepository;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
    public Role findBy(RoleId roleId) {
        Object result = this.sessionFactory
                .getCurrentSession()
                .createCriteria(Role.class)
                .add(Restrictions.eq("id", roleId))
                .setMaxResults(1)
                .uniqueResult();

        return (Role) result;
    }
}
