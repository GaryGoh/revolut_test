package com.enhancement;

import com.domain.Account;
import com.persistence.AccountDB;
import com.repository.AccountRepository;

import java.io.Serializable;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class AccountEnhancement implements AccountRepository {
    private static final Logger log = LoggerFactory.getLogger(AccountRepository.class);
    private static AccountEnhancement instance;

    private AccountEnhancement(){}

    public static AccountEnhancement getInstance() {
        if (instance == null) {
            instance = new AccountEnhancement();
        }
        return instance;
    }

    @Override
    public Account find(Serializable serializable) {
        if (serializable == null) {
            throw new IllegalArgumentException("Illegal input: input must not be null");
        }
        if (serializable instanceof Integer) {
            int id = (Integer) serializable;
            Account entity = AccountDB.getById(id);
            if (entity == null) {
                log.debug("Cannot find out this account with criteria - {}", id);
                throw new NullPointerException("Cannot find out this account with criteria - " + id);
            } else {
                return entity;
            }
        }
        return null;
    }

    @Override
    public List<Account> findAll() {
        return AccountDB.getAll();
    }
}
