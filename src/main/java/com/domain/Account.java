package com.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.persistence.AccountDB;

import java.math.BigDecimal;

public class Account {
    private int id;
    private String accountNumber;
    private String iban;
    private String firstName;
    private String lastName;
    private BigDecimal balance;

    public Account() {
        // Needed by Jackson deserialization
    }

    public Account(int id, String firstName, String lastName, String accountNumber, String iban, BigDecimal balance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountNumber = accountNumber;
        this.iban = iban;
        this.balance = balance;
    }

    @JsonProperty
    public int getId() {
        return id;
    }

    @JsonProperty
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty
    public String getLastName() {
        return lastName;
    }

    @JsonProperty
    public String getAccountNumber() {
        return accountNumber;
    }

    @JsonProperty
    public String getIban() {
        return iban;
    }

    @JsonProperty
    public BigDecimal getBalance() {
        return balance;
    }

    public void updateBalance(BigDecimal amount) {
        balance = balance.add(amount);
        AccountDB.save(this);
    }
}
