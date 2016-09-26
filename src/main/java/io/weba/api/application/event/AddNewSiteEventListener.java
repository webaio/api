package io.weba.api.application.event;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountForGivenIdNotFound;
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
    public void handle(AddNewSiteEvent addNewSiteEvent) throws AccountForGivenIdNotFound {
        Account account = this
                .accountRepository
                .findBy(addNewSiteEvent.accountId())
                .orElseThrow(AccountForGivenIdNotFound::new);

        Site site = new Site(
                addNewSiteEvent.siteId(),
                addNewSiteEvent.name(),
                addNewSiteEvent.timezone(),
                account,
                addNewSiteEvent.url()
        );

        this.siteRepository.add(site);
    }
}
