package io.weba.api.infrastructure.domain.site.inmemory;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.site.*;
import io.weba.api.infrastructure.domain.site.file.TrackerRepositoryImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SiteRepositoryImpl implements SiteRepository {
    private final List<Site> list;
    private final TrackerRepository trackerRepository;


    public SiteRepositoryImpl() {
        this.list = new ArrayList<>();
        this.trackerRepository = new TrackerRepositoryImpl(getClass().getClassLoader());
    }

    @Override
    public void add(Site site) {
        this.list.add(site);
    }

    @Override
    public void remove(Site site) {
        this.list.remove(site);
    }

    @Override
    public Sites findBy(Account account) {
        return null;
    }

    @Override
    public Sites findAll() {
        return null;
    }

    @Override
    public Optional<Site> findBy(UUID siteId, Account account) {
        Site foundSite = null;

        for (Site site : this.list) {
            if (site.getId().equals(siteId) && site.getAccount().getId().equals(account.getId())) {
                foundSite = site;
            }
        }

        return Optional.ofNullable(foundSite).map(
                site -> {
                    this
                            .trackerRepository
                            .findBy(site)
                            .map(resultTracker -> {
                                site.resolveTracker(resultTracker);

                                return resultTracker;
                            });

                    return site;
                }
        );
    }
}
