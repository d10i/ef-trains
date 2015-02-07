package com.joinef.eftrains.dropwizard.spring;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

import javax.validation.constraints.NotNull;

public class SpringServiceConfiguration extends Configuration {

    @NotNull
    @JsonProperty
    private SpringConfiguration spring;

    public SpringConfiguration getSpring() {
        return this.spring;
    }

}
