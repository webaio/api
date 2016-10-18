package io.weba.api.application.event;

import com.fasterxml.jackson.annotation.JsonView;
import io.weba.api.application.base.DomainEvent;
import io.weba.api.domain.timezone.Timezone;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.weba.api.ui.rest.view.View;
import org.hibernate.validator.constraints.Email;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class CreateSiteEvent implements DomainEvent {
    @NotNull
    @Size(min = 2, max = 30)
    public String name;

    @NotNull
    public String timezone;

    @NotNull
    @org.hibernate.validator.constraints.URL
    public String url;

    @NotNull
    public String accountUuid;

    @Email
    public String username;

    private UUID siteId;

    public CreateSiteEvent() {
        this.siteId = UUID.randomUUID();
    }

    public String name() {
        return this.name;
    }

    @JsonView(View.SiteCreate.class)
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

    public UUID accountUuid() {
        return UUID.fromString(this.accountUuid);
    }

    public String username() {
        return this.username;
    }
}
