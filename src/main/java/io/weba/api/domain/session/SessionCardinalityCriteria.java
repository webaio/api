package io.weba.api.domain.session;

import java.util.Date;
import java.util.UUID;

public class SessionCardinalityCriteria {
    public final Date from;
    public final Date to;
    public final UUID trackerIdentity;
    public final Interval interval;

    public SessionCardinalityCriteria(Date from, Date to, UUID trackerIdentity, Interval interval) {
        this.from = from;
        this.to = to;
        this.trackerIdentity = trackerIdentity;
        this.interval = interval;
    }
}
