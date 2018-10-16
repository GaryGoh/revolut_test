package com.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.util.AccountInfoGenerator;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Account {
    private String id;
    private String accountNumber;
    private String iban;
    private String firstName;
    private String lastName;
    private BigDecimal balance;
    private Timestamp createdAt;

    public Account() {
        // Needed by Jackson deserialization
        this.createdAt = new java.sql.Timestamp(new java.util.Date().getTime());
    }

    public Account(String id, String firstName, String lastName, String accountNumber, String iban, BigDecimal balance, Timestamp createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountNumber = accountNumber;
        this.iban = iban;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public Account(String id, BigDecimal balance) {
        this.id = id;
        AccountInfoGenerator gen = new AccountInfoGenerator();
        this.accountNumber = gen.generateAccountNumber();
        this.iban = gen.generateAccountIban();
        this.balance = balance;
        this.createdAt = new java.sql.Timestamp(new java.util.Date().getTime());
    }

    @JsonProperty
    public String getId() {
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

    @JsonProperty
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setBalance(BigDecimal amount) {
        balance = amount;
    }
}
