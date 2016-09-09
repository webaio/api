package io.weba.api.domain.site;

import io.weba.api.domain.account.Account;

public interface SiteRepository {
    void add(Site site);

    Sites findAllFor(Account account);
}
