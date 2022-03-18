package com.github.garethjevans.binding;

import org.apache.tomcat.util.IntrospectionUtils;

public class BindingPropertySource implements IntrospectionUtils.PropertySource {

    @Override
    public String getProperty(String key) {
        // system property first

        String value = getSystemProperty(key);
        if (value != null) {
            return value;
        }

        // then environment variable
        value = getEnvironmentVariable(key);
        if (value != null) {
            return value;
        }

        // then environment variable in lowercase with dots
        value = getEnvironmentVariable(key.toUpperCase().replace('.', '_'));
        if (value != null) {
            return value;
        }

        // TODO fall back on reading from a binding
        return null;
    }

    private String getEnvironmentVariable(String key) {
        System.out.println("getEnvironmentVariable(" + key + ")");
        return System.getenv(key);
    }

    private String getSystemProperty(String key) {
        System.out.println("getSystemProperty(" + key + ")");
        return System.getProperty(key);
    }
}
