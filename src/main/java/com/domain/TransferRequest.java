package com.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TransferRequest {
    private String transactionId;
    private Integer fromAccountId;
    private Integer toAccountId;
    private BigDecimal amount;

    public TransferRequest() {
        // Needed by Jackson deserialization
    }

    public TransferRequest(Integer fromAccountId, Integer toAccountId, Integer amount) {
        this.transactionId = "00000";
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = new BigDecimal(amount);
    }

    @JsonProperty
    public String getId() {
        return transactionId;
    }

    @JsonProperty
    public Integer getFromAccountId() {
        return fromAccountId;
    }

    @JsonProperty
    public Integer getToAccountId() {
        return toAccountId;
    }

    @JsonProperty
    public BigDecimal getAmount() {
        return amount;
    }
}
