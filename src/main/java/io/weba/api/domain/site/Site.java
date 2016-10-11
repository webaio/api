package io.weba.api.domain.site;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.timezone.Timezone;
import io.weba.api.infrastructure.postgres.type.TimezoneType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.net.URL;
import java.util.UUID;

@TypeDefs({
        @TypeDef(name = "timezone", typeClass = TimezoneType.class)
})
@Table(name = "sites")
@Entity
public class Site {
    @Id
    @Column
    private UUID id;

    @Column(name = "name")
    private String name;

    @Basic
    @Column
    @Type(type = "timezone")
    private Timezone timezone;

    @Column(name = "url")
    private URL url;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Transient
    private Tracker tracker;

    public Site(UUID id, String name, Timezone timezone, Account account, URL url) {
        this.id = id;
        this.name = name;
        this.timezone = timezone;
        this.account = account;
        this.url = url;
    }

    public Site() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Timezone getTimezone() {
        return timezone;
    }

    public Account getAccount() {
        return account;
    }

    public URL getUrl() {
        return url;
    }

    public void resolveTracker(Tracker tracker) {
        this.tracker = tracker;
    }

    public Tracker getTracker() {
        return this.tracker;
    }
}
