package io.weba.api.infrastructure.domain.site.postgres;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.site.Site;
import io.weba.api.domain.site.SiteRepository;
import io.weba.api.domain.site.Sites;
import io.weba.api.domain.site.TrackerRepository;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SiteRepositoryImpl implements SiteRepository {
    private final SessionFactory sessionFactory;
    private final TrackerRepository trackerRepository;

    @Autowired
    public SiteRepositoryImpl(SessionFactory sessionFactory, TrackerRepository trackerRepository) {
        this.sessionFactory = sessionFactory;
        this.trackerRepository = trackerRepository;
    }

    @Override
    @Transactional
    public void add(Site site) {
        this.sessionFactory.getCurrentSession().save(site);
    }

    @Override
    @Transactional
    public void remove(Site site) {
        this.sessionFactory.getCurrentSession().delete(site);
    }

    @Override
    @Transactional
    public Sites findBy(Account account) {
        Sites sites = new Sites();
        @SuppressWarnings({"unchecked"})
        List<Site> list = this.sessionFactory
                .getCurrentSession()
                .createCriteria(Site.class)
                .add(Restrictions.eq("account", account))
                .list();

        list.stream().forEach(item -> sites.add((Site) item));

        return sites;
    }

    @Override
    @Transactional
    public Sites findAll() {
        Sites sites = new Sites();
        @SuppressWarnings({"unchecked"})
        List<Site> list = this.sessionFactory
                .getCurrentSession()
                .createCriteria(Site.class)
                .list();

        list.stream().forEach(item -> sites.add((Site) item));

        return sites;
    }

    @Override
    @Transactional
    public Optional<Site> findBy(UUID siteId, Account account) {
        Object result = this.sessionFactory
                .getCurrentSession()
                .createCriteria(Site.class)
                .add(Restrictions.eq("id", siteId))
                .add(Restrictions.eq("account", account))
                .setMaxResults(1)
                .uniqueResult();

        return Optional.ofNullable((Site) result).map(site -> {
            this.trackerRepository.findBy(site).map(resultTracker -> {
                site.resolveTracker(resultTracker);

                return resultTracker;
            });

            return site;
        });
    }

    @Override
    @Transactional
    public Optional<Site> findBy(String name, Account account) {
        Object result = this.sessionFactory
                .getCurrentSession()
                .createCriteria(Site.class)
                .add(Restrictions.eq("name", name))
                .add(Restrictions.eq("account", account))
                .setMaxResults(1)
                .uniqueResult();

        return Optional.ofNullable((Site) result);
    }
}
