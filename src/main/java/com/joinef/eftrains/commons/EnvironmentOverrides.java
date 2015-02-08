package com.joinef.eftrains.commons;

import com.joinef.eftrains.commons.exception.GenericExceptionMapper;
import com.yammer.dropwizard.config.Environment;

import javax.ws.rs.ext.ExceptionMapper;
import java.util.HashSet;
import java.util.Set;

public class EnvironmentOverrides {

    public void overrideErrorHandlingToReturnJson(Environment environment) {
        Set singletons = environment.getJerseyResourceConfig().getSingletons();
        Set<Object> singletonsToRemove = new HashSet<Object>();
        for (Object singleton : singletons) {
            if (singleton instanceof ExceptionMapper && singleton.getClass().getName().startsWith("com.yammer.dropwizard.jersey.")) {
                singletonsToRemove.add(singleton);
            }
        }

        for (Object singleton : singletonsToRemove) {
            singletons.remove(singleton);
        }
        environment.addProvider(new GenericExceptionMapper());
    }
}
