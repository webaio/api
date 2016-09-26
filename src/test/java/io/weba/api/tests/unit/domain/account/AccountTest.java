package io.weba.api.tests.unit.domain.account;

import io.weba.api.domain.account.Account;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class AccountTest {
    @Test
    public void itGetsAccountNameAndId() {
        Account test = new Account("Test");

        assertEquals(test.getName(), "Test");
        assertEquals(test.getId().getClass(), UUID.class);
    }
}
