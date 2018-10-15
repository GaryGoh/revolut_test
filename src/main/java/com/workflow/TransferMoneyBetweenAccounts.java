package com.workflow;

import com.domain.Account;
import com.domain.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class TransferMoneyBetweenAccounts {
    private static final Logger log = LoggerFactory.getLogger(TransferMoneyBetweenAccounts.class);
    private static TransferMoneyBetweenAccounts instance;
    private TransferMoneyBetweenAccounts(){

    }

    public synchronized static TransferMoneyBetweenAccounts getInstance() {
        if (instance == null) {
            instance = new TransferMoneyBetweenAccounts();
        }
        return instance;
    }

    public Result transferMoney(Account fromAccount, Account toAccount, BigDecimal amount) {
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            log.debug("Account dose not have valid balance - {} to transfer the amount of money - {}", fromAccount.getBalance(), amount);
            return new Result(false, "000");
        }

        fromAccount.updateBalance(amount.negate());
        toAccount.updateBalance(amount);

        return new Result(true, "100");
    }
}
