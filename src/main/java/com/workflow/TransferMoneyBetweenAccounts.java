package com.workflow;

import com.MoneyTransferConstants;
import com.domain.Account;
import com.domain.Result;
import com.domain.TransferRequest;
import com.enhancement.AccountEnhancement;
import com.service.AccountService;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class TransferMoneyBetweenAccounts {
    private static final Logger log = LoggerFactory.getLogger(TransferMoneyBetweenAccounts.class);
    private static TransferMoneyBetweenAccounts instance;
    private AccountService accountService;
    private DBI dbi;

    public TransferMoneyBetweenAccounts(DBI dbi) {
        this.dbi = dbi;
        accountService = new AccountService(new AccountEnhancement(dbi));
    }

    public synchronized static TransferMoneyBetweenAccounts getInstance(DBI dbi) {
        if (instance == null) {
            instance = new TransferMoneyBetweenAccounts(dbi);
        }
        return instance;
    }

    public Result transferMoney(TransferRequest request) {
        return dbi.inTransaction((handle, transactionStatus) -> {
            Account fromAccount;
            Account toAccount;
            String fromCriteria = request.getFromAccount();
            String toCriteria = request.getToAccount();
            BigDecimal amount = request.getAmount();

            if (amount.intValue() < 0) {
                log.debug("Transfer amount is invalid, please check amount - {}", amount);
                return new Result(false, "020", MoneyTransferConstants.TRANSFER_AMOUNT_INVALID);
            }

            // check from-account
            if (fromCriteria.length() == MoneyTransferConstants.VALID_ACCOUNT_LENGTH) {
                fromAccount = accountService.findByAccountNumber(fromCriteria);
            } else if (fromCriteria.length() == MoneyTransferConstants.VALID_IBAN_LENGTH) {
                fromAccount = accountService.findByIban(fromCriteria);
            } else {
                log.debug("From-Account dose not exist - by searching {}", fromCriteria);
                return new Result(false, "001", MoneyTransferConstants.FROM_ACCT_NO_EXIT);
            }

            // check to-account
            if (toCriteria.length() == MoneyTransferConstants.VALID_ACCOUNT_LENGTH) {
                toAccount = accountService.findByAccountNumber(toCriteria);
            } else if (toCriteria.length() == MoneyTransferConstants.VALID_IBAN_LENGTH) {
                toAccount = accountService.findByIban(toCriteria);
            } else {
                log.debug("To-Account dose not exist - by searching {}", toCriteria);
                return new Result(false, "002", MoneyTransferConstants.TO_ACCT_NO_EXIT);
            }

            if (fromAccount == null) {
                log.debug("From-Account dose not exist - by searching {}", fromCriteria);
                return new Result(false, "001", MoneyTransferConstants.FROM_ACCT_NO_EXIT);
            }

            if (toAccount == null) {
                log.debug("To-Account dose not exist - by searching {}", toCriteria);
                return new Result(false, "002", MoneyTransferConstants.TO_ACCT_NO_EXIT);
            }

            if (fromAccount.getBalance().compareTo(amount) < 0) {
                log.debug("From-Account dose not have valid balance - {} to transfer the amount of money - {}", fromAccount.getBalance(), amount);
                return new Result(false, "100", MoneyTransferConstants.INVALID_BALANCE);
            }

            fromAccount.setBalance(fromAccount.getBalance().add(amount.negate()));
            toAccount.setBalance(toAccount.getBalance().add(amount));
            accountService.updateAccountBalance(fromAccount);
            accountService.updateAccountBalance(toAccount);

            return new Result(true, "000", MoneyTransferConstants.SUCCESS);
        });
    }
}
