package io.weba.api.tests.unit.domain.session;

import io.weba.api.domain.session.Interval;
import io.weba.api.domain.session.SessionCardinalityCriteria;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class SessionCardinalityCriteriaTest {
    @Test
    public void itShouldContainsCriteriaData() {
        Date from = new Date();
        Date to = new Date();
        UUID uuid = UUID.randomUUID();

        SessionCardinalityCriteria sessionCardinalityCriteria = new SessionCardinalityCriteria(
                from,
                to,
                uuid,
                Interval.DAY
        );

        assertSame(from, sessionCardinalityCriteria.from);
        assertSame(to, sessionCardinalityCriteria.to);
        assertSame(uuid, sessionCardinalityCriteria.trackerIdentity);
        assertSame(Interval.DAY, sessionCardinalityCriteria.interval);
    }
}