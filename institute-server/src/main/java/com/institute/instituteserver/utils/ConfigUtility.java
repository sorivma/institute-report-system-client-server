package com.institute.instituteserver.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@Configuration
public class ConfigUtility {

    private final Environment environment;

    @Autowired
    public ConfigUtility(Environment environment) {
        this.environment = environment;
    }

    public String getProperty(String propertyKey) {
        return environment.getProperty(propertyKey);
    }
}
