package io.weba.api.unit.domain;

import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountId;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
    @Test
    public void itGetsAccountNameAndId() {
        Account test = new Account("Test");

        assertEquals(test.getName(), "Test");
        assertEquals(test.getId().getClass(), AccountId.class);
    }
}
