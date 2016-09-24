package io.weba.api.domain.site;

import io.weba.api.domain.account.Account;

import java.util.Optional;
import java.util.UUID;

public interface SiteRepository {
    void add(Site site);

    void remove(Site site);

    Sites findBy(Account account);

    Sites findAll();

    Optional<Site> findBy(UUID siteId, Account account);
}
