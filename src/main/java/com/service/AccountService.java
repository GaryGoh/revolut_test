package com.service;

import java.util.Random;

public class AccountService {

    public static void main(String[] args) {
            long a = java.util.UUID.randomUUID().getMostSignificantBits();
        Double d = Math.abs(Math.E * Math.sin(a)) * 100000000;
        Random r = new Random(a);
        System.out.println(r.nextInt());
    }
}
