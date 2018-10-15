package com.util;

import java.util.Random;

public abstract class AbstractAccountInfoGenerator implements UUIDGenerator {

    /**
     * Generate the universally unique id for account info used.
     *
     * @return the UUID for account info.
     */
    @Override
    public String generateUUId() {
        return java.util.UUID.randomUUID().toString();
    }

    abstract String generateAccountNumber();

    abstract String generateAccountIban();

}
