package io.weba.api.application.event;

import io.weba.api.application.base.DomainEvent;
import io.weba.api.domain.account.Account;
import io.weba.api.domain.timezone.Timezone;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class AddNewSiteEvent implements DomainEvent {
    @NotNull
    @Size(min = 2, max = 30)
    public String name;

    @NotNull
    public String timezone;

    @NotNull
    @org.hibernate.validator.constraints.URL
    public String url;

    public Account account;

    private UUID siteId;

    public AddNewSiteEvent() {
        this.siteId = UUID.randomUUID();
    }

    public String name() {
        return this.name;
    }

    public Account account() {
        return this.account;
    }

    public UUID siteId() {
        return this.siteId;
    }

    public URL url() {
        try {
            return new URL(this.url);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public Timezone timezone() {
        return new Timezone(this.timezone);
    }
}
