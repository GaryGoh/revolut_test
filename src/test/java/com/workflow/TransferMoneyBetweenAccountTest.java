package com.workflow;

import com.db.TestDbRule;
import com.domain.Account;
import com.domain.Result;
import com.domain.TransferRequest;
import com.persistence.AccountDAO;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.Assert.*;

public class TransferMoneyBetweenAccountTest {

    private static final String MIKE_ACCT_ID = "11";
    private static final String GARY_ACCT_ID = "12";
    private static final String JULIA_ACCT_ID = "13";

    private static final String MIKE_ACCT_NUM = "53279468";
    private static final String GARY_ACCT_NUM = "85274285";
    private static final String JULIA_ACCT_NUM = "74390323";
    private static final String NULL_ACCT_NUM = "23424234";

    private static final String MIKE_ACCT_IBAN = "IBAN5327946853279468";
    private static final String GARY_ACCT_IBAN = "IBAN8527428585274285";
    private static final String JULIA_ACCT_IBAN = "IBAN7439032374390323";
    private static final String NULL_ACCT_IBAN = "IBAN2342423423424234";

    @Rule
    public TestDbRule db = new TestDbRule();

    @Before
    public void setUp() throws Exception {
        db.getDbi().useHandle(h -> {
            Account mike = new Account(MIKE_ACCT_ID, "Mike", "LN1", MIKE_ACCT_NUM, MIKE_ACCT_IBAN, new BigDecimal(5000.00), new Timestamp(1539698959797L));
            Account gary = new Account(GARY_ACCT_ID, "Gary", "LN2", GARY_ACCT_NUM, GARY_ACCT_IBAN, new BigDecimal(0.00), new Timestamp(1539698959797L));
            Account julia = new Account(JULIA_ACCT_ID, "Julia", "LN3", JULIA_ACCT_NUM, JULIA_ACCT_IBAN, new BigDecimal(300.00), new Timestamp(1539698959797L));
            AccountDAO dao = h.attach(AccountDAO.class);
            dao.insertAccount(mike);
            dao.insertAccount(gary);
            dao.insertAccount(julia);
        });
    }


    @Test
    public void shouldNotTransferWhenInvalidBalance() {
        db.getDbi().useHandle(handle -> {
            BigDecimal account2Balance;
            AccountDAO dao = handle.attach(AccountDAO.class);
            Account fromAccount;
            Account toAccount = dao.findByAccountNumber(JULIA_ACCT_NUM);
            account2Balance = toAccount.getBalance();

            TransferRequest request = new TransferRequest(GARY_ACCT_NUM, JULIA_ACCT_NUM, 300);
            Result result = new TransferMoneyBetweenAccounts(db.getDbi()).transferMoney(request);
            assertFalse(result.getSuccess());

            fromAccount = dao.find(GARY_ACCT_ID);
            toAccount = dao.find(JULIA_ACCT_ID);
            assertEquals(new BigDecimal("0.000"), fromAccount.getBalance());
            assertEquals(account2Balance, toAccount.getBalance());
        });
    }

    @Test
    public void shouldTransferWhenHasBalance() {
        db.getDbi().useHandle(handle -> {
            BigDecimal account1Balance;
            BigDecimal account2Balance;
            AccountDAO dao = handle.attach(AccountDAO.class);
            Account fromAccount = dao.findByAccountNumber(MIKE_ACCT_NUM);
            Account toAccount = dao.findByAccountNumber(JULIA_ACCT_NUM);
            account1Balance = fromAccount.getBalance();
            account2Balance = toAccount.getBalance();

            TransferRequest request = new TransferRequest(MIKE_ACCT_NUM, JULIA_ACCT_NUM, 300);
            Result result = new TransferMoneyBetweenAccounts(db.getDbi()).transferMoney(request);
            assertTrue(result.getSuccess());

            fromAccount = dao.find(MIKE_ACCT_ID);
            toAccount = dao.find(JULIA_ACCT_ID);
            BigDecimal amount = new BigDecimal(300);

            assertEquals(account1Balance.subtract(amount), fromAccount.getBalance());
            assertEquals(account2Balance.add(amount), toAccount.getBalance());
        });
    }

    @Test
    public void shouldNotTransferWhenAmountIsInvalid() {
        db.getDbi().useHandle(handle -> {
            TransferRequest request = new TransferRequest(MIKE_ACCT_NUM, JULIA_ACCT_NUM, -100);
            Result result = new TransferMoneyBetweenAccounts(db.getDbi()).transferMoney(request);
            assertFalse(result.getSuccess());
        });
    }

    @Test
    public void shouldNotTransferWhenFromAccountNoExist() {
        db.getDbi().useHandle(handle -> {
            TransferRequest request = new TransferRequest(NULL_ACCT_NUM, JULIA_ACCT_NUM, 300);
            Result result = new TransferMoneyBetweenAccounts(db.getDbi()).transferMoney(request);
            assertFalse(result.getSuccess());
        });
    }

    @Test
    public void shouldNotTransferWhenTomAccountNoExist() {
        db.getDbi().useHandle(handle -> {
            TransferRequest request = new TransferRequest(MIKE_ACCT_NUM, NULL_ACCT_NUM, 300);
            Result result = new TransferMoneyBetweenAccounts(db.getDbi()).transferMoney(request);
            assertFalse(result.getSuccess());
        });
    }
}
