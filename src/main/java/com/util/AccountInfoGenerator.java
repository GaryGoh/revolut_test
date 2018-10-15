package com.util;

import java.util.Random;

public class AccountInfoGenerator extends AbstractAccountInfoGenerator {

    @Override
    public String generateAccountNumber() {
        long val = java.util.UUID.randomUUID().getMostSignificantBits();
        Double dVal = Math.abs(Math.E * Math.sin(val)) * 100000000;
        return String.valueOf(dVal.intValue());
    }

    @Override
    public String generateAccountIban() {
        StringBuilder iban = new StringBuilder("IBAN");
        iban.append(generateAccountNumber());
        Random suffix = new Random(Long.valueOf(generateAccountNumber()));
        iban.append(String.valueOf(suffix.nextInt()));
        return iban.toString();
    }
}
