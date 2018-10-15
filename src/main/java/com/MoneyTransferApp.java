package com;

import com.resource.TransferResource;
import io.dropwizard.Application;
//import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
//import org.skife.jdbi.v2.DBI;

public class MoneyTransferApp extends Application<MoneyTransferConfig> {

    public static void main(String[] args) throws Exception {
        new MoneyTransferApp().run(args);
    }

    @Override
    public void run(MoneyTransferConfig config, Environment env) {
//        final TransferResource transferResource = new TransferResource();
//        final DBIFactory factory = new DBIFactory();
//        final DBI jdbi = factory.build(env, config.getDataSourceFactory(), "dbi");
        env.jersey().register(new TransferResource());
    }
}
