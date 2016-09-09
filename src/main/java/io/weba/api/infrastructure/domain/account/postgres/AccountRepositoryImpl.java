package io.weba.api.infrastructure.domain.account.postgres;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountRepository;
import io.weba.api.domain.account.Accounts;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
                .createCriteria(Account.class)
                .add(Restrictions.eq("id", accountId))
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
                .list();

        list.stream().forEach(item->accounts.add((Account) item));

        return accounts;
    }
}
