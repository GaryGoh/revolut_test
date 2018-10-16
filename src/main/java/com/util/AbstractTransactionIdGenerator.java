package com.util;

    public abstract class AbstractTransactionIdGenerator implements UUIDGenerator {

    /**
     * Generate the universally unique transaction id for generic type of transaction
     *
     * @return the UUID for the transaction.
     */
    @Override
    public String generateUUId() {
        return "TID" + java.util.UUID.randomUUID();
    }
}
