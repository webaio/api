package io.weba.api.domain.session;

import java.util.Date;

public class SessionCardinality {
    public final Date date;
    public final long counter;

    public SessionCardinality(Date date, long counter) {
        this.date = date;
        this.counter = counter;
    }
}
