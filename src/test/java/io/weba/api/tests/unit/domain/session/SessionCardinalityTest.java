package io.weba.api.tests.unit.domain.session;

import io.weba.api.domain.session.SessionCardinality;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class SessionCardinalityTest {
    @Test
    public void itShouldContainsDateAndCounter() {
        Date date = new Date();
        long counter = 1000L;

        SessionCardinality sessionCardinality = new SessionCardinality(
                date,
                counter
        );

        assertEquals(date, sessionCardinality.date);
        assertEquals(counter, sessionCardinality.counter);
    }
}