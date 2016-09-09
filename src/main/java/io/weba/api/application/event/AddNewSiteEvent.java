package io.weba.api.application.event;

import io.weba.api.application.base.DomainEvent;
import io.weba.api.domain.timezone.Timezone;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddNewSiteEvent implements DomainEvent {
    @NotNull
    @Size(min = 2, max = 30)
    public String name;

    @NotNull
    public String timezone;

    @NotNull
    @org.hibernate.validator.constraints.URL
    public String url;

    public UUID accountId;

    private UUID siteId;

    public AddNewSiteEvent() {
        this.siteId = UUID.randomUUID();
    }

    public String name() {
        return this.name;
    }

    public UUID accountId() {
        return this.accountId;
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
