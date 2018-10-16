package com.enhancement;

import com.domain.Account;
import com.persistence.AccountDAO;
import com.repository.AccountRepository;

import java.io.Serializable;
import java.util.List;

import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This account enhancement represents the implementation of AccountDAO.
 * it is used to AccountService {@link com.service.AccountService}
 *
 */
public class AccountEnhancement implements AccountRepository {
    private static final Logger log = LoggerFactory.getLogger(AccountRepository.class);
    private static AccountEnhancement instance;
    private DBI dbi;

    public AccountEnhancement(DBI dbi) {
        this.dbi = dbi;
    }

    public static AccountEnhancement getInstance(DBI dbi) {
        if (instance == null) {
            instance = new AccountEnhancement(dbi);
        }
        return instance;
    }

    @Override
    public Account find(Serializable serializable) {
        if (serializable == null) {
            throw new IllegalArgumentException("Illegal input: input must not be null");
        }
        if (serializable instanceof String) {
            dbi.inTransaction((handle, transactionStatus) -> {
                String id = (String) serializable;
                Account entity = handle.attach(AccountDAO.class).find(id);
                if (entity == null) {
                    log.debug("Cannot find out this account with criteria - {}", id);
                    throw new NullPointerException("Cannot find out this account with criteria - " + id);
                }
                return entity;
            });

        }
        return null;
    }

    @Override
    public List<Account> findAll() {
        try {
            return dbi.inTransaction((handle, transactionStatus) -> {
                return handle.attach(AccountDAO.class).findAll();
            });
        } catch (RuntimeException e) {
            log.debug(e.getMessage());
        }
        return null;
    }

    @Override
    public Account findByAccountNumber(String acctNum) {
        try {
            return dbi.inTransaction((handle, transactionStatus) -> {
                return handle.attach(AccountDAO.class).findByAccountNumber(acctNum);
            });
        } catch (RuntimeException e) {
            log.debug(e.getMessage());
        }
        return null;
    }

    @Override
    public Account findByiban(String iban) {
        try {
            return dbi.inTransaction((handle, transactionStatus) -> {
                return handle.attach(AccountDAO.class).findByIban(iban);
            });
        } catch (RuntimeException e) {
            log.debug(e.getMessage());
        }
        return null;
    }

    @Override
    public void updateAccount(Account account) {
        try {
            dbi.useHandle(handle -> {
                handle.attach(AccountDAO.class).updateBalance(account);
            });
        } catch (RuntimeException e) {
            log.debug(e.getMessage());
        }
    }
}
