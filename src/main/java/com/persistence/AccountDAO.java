package com.persistence;

import com.domain.Account;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RegisterMapper(AccountDAO.AccountRecordMapper.class)
public interface AccountDAO {
    @SqlQuery("select * from accounts")
    List<Account> findAll();

    @SqlQuery("select * from accounts where id = :id")
    Account find(@Bind("id") String id);

    @SqlQuery("select * from accounts where account_number = :accnum")
    Account findByAccountNumber(@Bind("accnum") String accountNumber);

    @SqlQuery("select * from accounts where iban = :iban")
    Account findByIban(@Bind("iban") String iban);

    @SqlUpdate("insert into accounts (id, first_name, last_name, account_number, iban, balance, created_at)" +
            " values (:id, :firstName, :lastName, :accountNumber, :iban, :balance, :createdAt)")
    void insertAccount(@BindBean Account account);

    @SqlUpdate("update accounts set balance = :balance where id = :id")
    int updateBalance(@BindBean Account account);

    class AccountRecordMapper implements ResultSetMapper<Account> {
        @Override
        public Account map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new Account(r.getString("id"),
                    r.getString("first_name"),
                    r.getString("last_name"),
                    r.getString("account_number"),
                    r.getString("iban"),
                    r.getBigDecimal("balance"),
                    r.getTimestamp("created_at"));
        }

    }
}
