package com.joinef.eftrains;

import com.joinef.eftrains.commons.EnvironmentOverrides;
import com.joinef.eftrains.dropwizard.spring.SpringService;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class EfTrains extends Service<EfTrainsConfiguration> {

    SpringService springService = new SpringService();

    public static void main(String[] args) throws Exception {
        new EfTrains().run(args);
    }

    @Override
    public void initialize(Bootstrap<EfTrainsConfiguration> bootstrap) {
        bootstrap.setName("ef-trains");
    }

    @Override
    public void run(EfTrainsConfiguration configuration, Environment environment) throws Exception {
        new EnvironmentOverrides().overrideErrorHandlingToReturnJson(environment);
        springService.run(configuration, environment);
    }
}
