package io.weba.api.application.event;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountWithGivenUuidNotFound;
import io.weba.api.domain.account.AccountRepository;
import io.weba.api.domain.role.Role;
import io.weba.api.domain.site.Site;
import io.weba.api.domain.site.SiteRepository;
import io.weba.api.domain.site.SiteWithGivenNameAlreadyExistsForAccount;
import io.weba.api.domain.user.User;
import io.weba.api.domain.user.UserRepository;
import io.weba.api.domain.user.UserWithGivenUsernameDoesNotExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CreateSiteEventListener {
    private final SiteRepository siteRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Autowired
    public CreateSiteEventListener(
            SiteRepository siteRepository,
            AccountRepository accountRepository,
            UserRepository userRepository
    ) {
        this.siteRepository = siteRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @EventListener
    public void handle(CreateSiteEvent createSiteEvent) throws
            AccountWithGivenUuidNotFound, UserWithGivenUsernameDoesNotExist, SiteWithGivenNameAlreadyExistsForAccount {
        Account account;
        User user = this.userRepository
                .findBy(createSiteEvent.username)
                .orElseThrow(UserWithGivenUsernameDoesNotExist::new);

        if (user.getRoles().getName().equals(Role.ROLE_SUPER_ADMIN)) {
            account = this.accountRepository
                    .findBy(createSiteEvent.accountUuid())
                    .orElseThrow(AccountWithGivenUuidNotFound::new);
        } else {
            account = this.accountRepository
                    .findBy(createSiteEvent.accountUuid(), createSiteEvent.username())
                    .orElseThrow(AccountWithGivenUuidNotFound::new);
        }

        this.siteRepository
                .findBy(createSiteEvent.name(), account)
                .orElseThrow(SiteWithGivenNameAlreadyExistsForAccount::new);

        Site site = new Site(
                createSiteEvent.siteId(),
                createSiteEvent.name(),
                createSiteEvent.timezone(),
                account,
                createSiteEvent.url()
        );

        this.siteRepository.add(site);
    }
}
