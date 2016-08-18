package io.weba.api.infrastructure.domain.account.postgres;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountId;
import io.weba.api.domain.account.AccountRepository;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public AccountRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void createNewAccount(Account account) {
        this.sessionFactory.getCurrentSession().save(account);
    }

    @Override
    @Transactional
    public Account findBy(AccountId accountId) {
        Object result = this.sessionFactory
                .getCurrentSession()
                .createCriteria(Account.class)
                .add(Restrictions.eq("id", accountId))
                .setMaxResults(1)
                .uniqueResult();

        return (Account) result;
    }
}
