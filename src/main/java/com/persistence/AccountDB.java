package com.persistence;

import com.domain.Account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * It has been deprecated as it only used for development without h2 DB.
 */
@Deprecated
public class AccountDB {
    private static Map<Integer, Account> accounts = new HashMap<Integer, Account>();

    static {
        accounts.put(1, new Account("1", "FN1", "LN1", "12345", "IB12345", new BigDecimal(2000), null));
        accounts.put(2, new Account("2", "FN2", "LN2", "342543", "IB342543", new BigDecimal(350), null));
        accounts.put(3, new Account("3", "FN3", "LN3", "766732", "IB766732", new BigDecimal(5000), null));
        accounts.put(4, new Account("4", "FN4", "LN4", "98763", "IB98763", new BigDecimal(0), null));
    }

    public static Account getById(int id) {
        return accounts.get(id);
    }

    public static List<Account> getAll() {
        List<Account> result = new ArrayList<Account>();
        for (Integer key : accounts.keySet()) {
            result.add(accounts.get(key));
        }
        return result;
    }

    public static int getCount() {
        return accounts.size();
    }

    public static void remove() {
        if (!accounts.keySet().isEmpty()) {
            accounts.remove(accounts.keySet().toArray()[0]);
        }
    }

    public static String save(Account account) {
        String result = "";
        if (accounts.get(account.getId()) != null) {
            result = "Updated Account with id=" + account.getId();
        } else {
            result = "Added Account with id=" + account.getId();
        }
        accounts.put(Integer.valueOf(account.getId()), account);
        return result;
    }
}
