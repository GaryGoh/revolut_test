package com;

import com.resource.TransferResource;
import com.util.LiquibaseMigrateOnBoot;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

public class MoneyTransferApp extends Application<MoneyTransferConfig> {

    public static void main(String[] args) throws Exception {
        new MoneyTransferApp().run(args);
    }

    @Override
    public void run(MoneyTransferConfig config, Environment env) {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(env, config.getDataSourceFactory(), "h2");
        env.jersey().register(new TransferResource(jdbi));
        env.lifecycle().manage(new LiquibaseMigrateOnBoot(
                () -> LiquibaseMigrateOnBoot.create(jdbi.open(), Handle::getConnection), "migration.xml"));
    }
}
