package io.weba.api.infrastructure.domain.account.postgres;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountRepository;
import io.weba.api.domain.account.Accounts;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public AccountRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void add(Account account) {
        this.sessionFactory.getCurrentSession().save(account);
    }

    @Override
    @Transactional
    public Optional<Account> findBy(UUID accountId) {
        Object result = this.sessionFactory
                .getCurrentSession()
                .createCriteria(Account.class, "account")
                .add(Restrictions.eq("account.id", accountId))
                .uniqueResult();

        return Optional.ofNullable((Account) result);
    }

    @Override
    @Transactional
    public Optional<Account> findBy(UUID accountUuid, String username) {
        Object result = this.sessionFactory
                .getCurrentSession()
                .createCriteria(Account.class, "account")
                .createAlias("account.users", "user")
                .add(Restrictions.eq("account.id", accountUuid))
                .add(Restrictions.eq("user.username", username))
                .setMaxResults(1)
                .uniqueResult();

        return Optional.ofNullable((Account) result);
    }

    @Override
    @Transactional
    public Optional<Account> findBy(String name) {
        Object result = this.sessionFactory
                .getCurrentSession()
                .createCriteria(Account.class)
                .add(Restrictions.eq("name", name))
                .setMaxResults(1)
                .uniqueResult();

        return Optional.ofNullable((Account) result);
    }


    @Override
    @Transactional
    public Accounts findAll() {
        Accounts accounts = new Accounts();
        @SuppressWarnings({"unchecked"})
        List<Account> list = this.sessionFactory
                .getCurrentSession()
                .createCriteria(Account.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();

        list.stream().forEach(item -> accounts.add((Account) item));

        return accounts;
    }
}
