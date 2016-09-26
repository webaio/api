package io.weba.api.infrastructure.domain.oauth.postgres;

import io.weba.api.domain.oauth.OauthClientDetails;
import io.weba.api.domain.oauth.OauthClientDetailsRepository;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OauthClientDetailsRepositoryImpl implements OauthClientDetailsRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public OauthClientDetailsRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(OauthClientDetails oauthClientDetails) {
        this.sessionFactory.getCurrentSession().save(oauthClientDetails);
    }

    @Override
    public Optional<OauthClientDetails> findBy(String clientId) {
        Object result = this.sessionFactory
                .getCurrentSession()
                .createCriteria(OauthClientDetails.class)
                .add(Restrictions.eq("clientId", clientId))
                .setMaxResults(1)
                .uniqueResult();

        return Optional.ofNullable((OauthClientDetails) result);
    }
}
