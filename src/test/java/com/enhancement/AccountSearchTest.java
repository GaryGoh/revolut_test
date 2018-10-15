package com.enhancement;


import com.domain.Account;
import com.persistence.AccountDB;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountSearchTest {

    @Test
    public void shouldGetCorrectAccount() {
        Account res = AccountEnhancement.getInstance().find(1);
        assertEquals(AccountDB.getById(1), res);
    }
}
