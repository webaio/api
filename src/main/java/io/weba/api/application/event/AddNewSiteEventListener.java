package io.weba.api.application.event;

import io.weba.api.domain.account.AccountWithGivenUuidNotFound;
import io.weba.api.domain.account.AccountRepository;
import io.weba.api.domain.site.Site;
import io.weba.api.domain.site.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AddNewSiteEventListener {
    private final SiteRepository siteRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public AddNewSiteEventListener(SiteRepository siteRepository, AccountRepository accountRepository) {
        this.siteRepository = siteRepository;
        this.accountRepository = accountRepository;
    }

    @EventListener
    public void handle(AddNewSiteEvent addNewSiteEvent) throws AccountWithGivenUuidNotFound {
        Site site = new Site(
                addNewSiteEvent.siteId(),
                addNewSiteEvent.name(),
                addNewSiteEvent.timezone(),
                addNewSiteEvent.account(),
                addNewSiteEvent.url()
        );

        this.siteRepository.add(site);
    }
}
