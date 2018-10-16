package com.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.rules.ExternalResource;
import org.skife.jdbi.v2.DBI;
import java.sql.Connection;


public class TestDbRule extends ExternalResource {
    private final String liquibaseConfig;
    private final String jdbcUrl;

    private DBI dbi;
    private HikariDataSource ds;

    public DBI getDbi() {
        return dbi;
    }

    public TestDbRule() {
        this("migration.xml", "jdbc:h2:mem:test");
    }

    public TestDbRule(String liquibaseConfig, String jdbcUrl) {
        this.liquibaseConfig = liquibaseConfig;
        this.jdbcUrl = jdbcUrl;
    }

    @Override
    protected void before() throws Throwable {
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(jdbcUrl);
        cfg.setUsername("sa");
        cfg.setPassword("");
        ds = new HikariDataSource(cfg);
        this.dbi = new DBI(ds);

        try (Connection connection = ds.getConnection()) {
            Liquibase liquibase = new Liquibase(
                    liquibaseConfig,
                    new ClassLoaderResourceAccessor(),
                    new JdbcConnection(connection)
            );
            liquibase.update("");
        }
    }

    @Override
    protected void after() {
        ds.close();
    }

}
