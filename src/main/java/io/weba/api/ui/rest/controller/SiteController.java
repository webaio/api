package io.weba.api.ui.rest.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.weba.api.application.base.DomainEventPublisher;
import io.weba.api.application.event.CreateSiteEvent;
import io.weba.api.domain.account.AccountRepository;
import io.weba.api.domain.site.SiteRepository;
import io.weba.api.domain.site.Sites;
import io.weba.api.ui.rest.view.View;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class SiteController {
    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private AccountRepository accountRepository;

    @JsonView(View.SiteCreate.class)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    @RequestMapping(method = RequestMethod.POST, value = "/site")
    public ResponseEntity<CreateSiteEvent> create(@Valid @RequestBody CreateSiteEvent createSiteEvent, Authentication principal) {
        createSiteEvent.username = principal.getName();
        this.domainEventPublisher.publish(createSiteEvent);

        return new ResponseEntity<>(createSiteEvent, HttpStatus.CREATED);
    }

//    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
//    @RequestMapping(method = RequestMethod.GET, value = "/site")
//    public ResponseEntity<Sites> getAllFromAccount(Principal principal) throws UserWithGivenUuidDoesNotExist {
//        Sites sites = this.siteRepository.findBy(this
//                .userRepository
//                .findBy(principal.getName())
//                .orElseThrow(UserWithGivenUuidDoesNotExist::new)
//                .getAccount()
//        );
//
//        return new ResponseEntity<>(sites, HttpStatus.OK);
//    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/admin/site/{accountUuid}")

    public ResponseEntity<Sites> getAll(@PathVariable String accountUuid) {
        return this
                .accountRepository
                .findBy(UUID.fromString(accountUuid))
                .map(account -> new ResponseEntity<>(this.siteRepository.findBy(account), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
//
//    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
//    @RequestMapping(method = RequestMethod.GET, value ="/site/{siteUuid}")
//    public ResponseEntity<Site> get(@PathVariable String siteUuid, Principal principal) throws UserWithGivenUuidDoesNotExist {
//        User user = this.userRepository.findBy(principal.getName()).orElseThrow(UserWithGivenUuidDoesNotExist::new);
//
//        return this.siteRepository
//                .findBy(UUID.fromString(siteUuid), user.getAccount())
//                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
//    @RequestMapping(method = RequestMethod.DELETE, value ="/site/{siteUuid}")
//    public ResponseEntity remove(@PathVariable String siteUuid, Principal principal) throws UserWithGivenUuidDoesNotExist {
//        User user = this.userRepository.findBy(principal.getName()).orElseThrow(UserWithGivenUuidDoesNotExist::new);
//
//        return this.siteRepository
//                .findBy(UUID.fromString(siteUuid), user.getAccount())
//                .map(result -> {
//                    this.siteRepository.remove(result);
//
//                    return new ResponseEntity(HttpStatus.NO_CONTENT);
//                })
//                .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
//    }
}
