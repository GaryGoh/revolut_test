package com.metrics;

import com.codahale.metrics.health.HealthCheck;
import com.persistence.AccountDB;

public class Check extends HealthCheck {
    private final String version;

    public Check(String version) {
        this.version = version;
    }

    @Override
    protected Result check() throws Exception {
        if (AccountDB.getCount() == 0) {
            return Result.unhealthy("No persons in DB! Version: " +
                    this.version);
        }
        return Result.healthy("OK with version: " + this.version +
                ". Persons count: " + AccountDB.getCount());
    }
}
