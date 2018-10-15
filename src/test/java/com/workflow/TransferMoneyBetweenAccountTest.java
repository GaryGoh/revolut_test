package com.workflow;

import com.domain.Account;
import com.enhancement.AccountEnhancement;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class TransferMoneyBetweenAccountTest {
    @Test
    public void shouldTransferWhenHasBalance() {
        Account account1 = AccountEnhancement.getInstance().find(1);
        Account account2 = AccountEnhancement.getInstance().find(2);
        BigDecimal account1Balance = account1.getBalance();
        BigDecimal account2Balance = account2.getBalance();
        BigDecimal amount = new BigDecimal(300);
        TransferMoneyBetweenAccounts.getInstance().transferMoney(account1, account2, amount);
        assertEquals(account1Balance.subtract(amount), account1.getBalance());
        assertEquals(account2Balance.add(amount), account2.getBalance());
    }
}
