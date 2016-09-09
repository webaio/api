package io.weba.api.infrastructure.domain.site.postgres;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.site.Site;
import io.weba.api.domain.site.SiteRepository;
import io.weba.api.domain.site.Sites;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SiteRepositoryImpl implements SiteRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public SiteRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void add(Site site) {
        this.sessionFactory.getCurrentSession().save(site);
    }

    @Override
    @Transactional
    public Sites findAllFor(Account account) {
        Sites sites = new Sites();
        @SuppressWarnings({"unchecked"})
        List<Site> list = this.sessionFactory
                .getCurrentSession()
                .createCriteria(Site.class)
                .add(Restrictions.eq("account", account))
                .list();

        list.stream().forEach(item->sites.add((Site) item));

        return sites;
    }
}
