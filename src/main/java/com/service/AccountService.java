package com.service;

import com.domain.Account;
import com.repository.AccountRepository;

import java.util.List;

public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account find(int id) {
        return (Account) accountRepository.find(id);
    }

    public Account findByAccountNumber(String accNum) {
        return accountRepository.findByAccountNumber(accNum);
    }

    public Account findByIban(String iban) {
        return accountRepository.findByiban(iban);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public void updateAccountBalance(Account account) {
        accountRepository.updateAccount(account);
    }
}
