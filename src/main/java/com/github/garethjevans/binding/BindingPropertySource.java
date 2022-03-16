package com.github.garethjevans.binding;

import org.apache.tomcat.util.IntrospectionUtils;
import org.apache.tomcat.util.security.PermissionCheck;

import java.security.Permission;
import java.util.PropertyPermission;

public class BindingPropertySource implements IntrospectionUtils.SecurePropertySource {

    @Override
    public String getProperty(String s) {
        return null;
    }

    @Override
    public String getProperty(String key, ClassLoader classLoader) {
        // system property first
        String value = getSystemProperty(key, classLoader);
        if (value != null) {
            return value;
        }

        // then environment variable
        value = getEnvironmentVariable(key, classLoader);
        if (value != null) {
            return value;
        }

        // then environment variable in lowercase with dots
        value = getEnvironmentVariable(key.toLowerCase().replace('_', '.'), classLoader);
        if (value != null) {
            return value;
        }

        // TODO fall back on reading from a binding

        return null;
    }

    private String getEnvironmentVariable(String key, ClassLoader classLoader) {

        if (classLoader instanceof PermissionCheck) {
            Permission p = new RuntimePermission("getenv." + key, (String)null);
            if (!((PermissionCheck)classLoader).check(p)) {
                return null;
            }
        }

        return null;
    }

    private String getSystemProperty(String key, ClassLoader classLoader) {
        if (classLoader instanceof PermissionCheck) {
            Permission p = new PropertyPermission(key, "read");
            if (!((PermissionCheck)classLoader).check(p)) {
                return null;
            }
        }

        return System.getProperty(key);
    }
}
