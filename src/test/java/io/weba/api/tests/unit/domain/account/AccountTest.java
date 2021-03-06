package io.weba.api.tests.unit.domain.account;

import io.weba.api.domain.account.Account;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    @Test
    public void itGetsAccountNameAndId() {
        Account test = new Account(UUID.randomUUID(), "Test");

        assertEquals(test.getName(), "Test");
        assertEquals(test.getId().getClass(), UUID.class);
    }
}
