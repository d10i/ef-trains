package com.joinef.eftrains;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.joinef.eftrains.dropwizard.spring.SpringServiceConfiguration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by dario.simonetti on 07/02/2015.
 */
public class EfTrainsConfiguration extends SpringServiceConfiguration {

    @NotEmpty
    @JsonProperty
    private String dbUsername;

    @NotEmpty
    @JsonProperty
    private String dbPassword;

    @NotEmpty
    @JsonProperty
    private String dbDriver;

    @NotEmpty
    @JsonProperty
    private String dbUrl;

    public String getDbUsername() {
        return dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public String getDbUrl() {
        return dbUrl;
    }
}
