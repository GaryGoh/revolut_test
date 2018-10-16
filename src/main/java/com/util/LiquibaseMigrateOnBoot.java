package com.util;

import io.dropwizard.lifecycle.Managed;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.util.function.Function;
import java.util.function.Supplier;

public class LiquibaseMigrateOnBoot implements Managed {
    public interface ClosableConnectionSupplier extends AutoCloseable, Supplier<Connection> {
    }

    public static <T extends AutoCloseable> ClosableConnectionSupplier create(T closable, Function<T, Connection> connectionSupplier) {
        return new ClosableConnectionSupplier() {
            @Override
            public void close() throws Exception {
                closable.close();
            }

            @Override
            public Connection get() {
                return connectionSupplier.apply(closable);
            }
        };
    }

    private final Supplier<ClosableConnectionSupplier> connectionSupplier;
    private final String changelogFile;

    public LiquibaseMigrateOnBoot(Supplier<ClosableConnectionSupplier> connectionSupplier, String changelogFile) {
        this.connectionSupplier = connectionSupplier;
        this.changelogFile = changelogFile;
    }

    @Override
    public void start() throws Exception {
        try (ClosableConnectionSupplier connectionSupplier = this.connectionSupplier.get()) {
            Liquibase liquibase = new Liquibase(
                    changelogFile,
                    new ClassLoaderResourceAccessor(),
                    new JdbcConnection(connectionSupplier.get())
            );
            liquibase.update("");
        }
    }

    @Override
    public void stop() {
    }
}
