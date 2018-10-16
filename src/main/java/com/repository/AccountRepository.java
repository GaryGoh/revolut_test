package com.repository;

import com.domain.Account;

public interface AccountRepository extends BaseRepository {
    Account findByAccountNumber(String accNum);

    Account findByiban(String iban);

    void updateAccount(Account account);
}
