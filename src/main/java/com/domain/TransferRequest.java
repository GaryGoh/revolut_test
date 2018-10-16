package com.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.util.TransactionIdGenerator;
import com.util.UUIDGenerator;

import java.math.BigDecimal;

public class TransferRequest {
    private String transactionId;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private UUIDGenerator transactionIdGenerator;

    public TransferRequest() {
        // Needed by Jackson deserialization
        this.transactionIdGenerator = new TransactionIdGenerator();
    }

    public TransferRequest(String fromAccount, String toAccount, Integer amount) {
        this();
        this.transactionId = transactionIdGenerator.generateUUId();
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = new BigDecimal(amount);
    }

    @JsonProperty
    public String getId() {
        return transactionId;
    }

    @JsonProperty
    public String getFromAccount() {
        return fromAccount;
    }

    @JsonProperty
    public String getToAccount() {
        return toAccount;
    }

    @JsonProperty
    public BigDecimal getAmount() {
        return amount;
    }
}
