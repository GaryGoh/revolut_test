package com.domain;

import org.junit.Test;

import java.math.BigDecimal;
import static junit.framework.Assert.assertNotNull;

public class TestAccount {
    @Test
    public void shouldGetCorrectAccount() {
        Account account = new Account("0001", new BigDecimal(300));
        assertNotNull(account.getId());
        assertNotNull(account.getAccountNumber());
        assertNotNull(account.getIban());
        assertNotNull(account.getBalance());
    }
}
