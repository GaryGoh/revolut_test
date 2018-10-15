package com.util;


public class TransactionIdGenerator extends AbstractTransactionIdGenerator {

    public String stardardTransactionID() {
        return super.generateUUId();
    }
}
