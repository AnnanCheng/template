package com.annan.backend;

public interface DataSourceConfig {

    String getDriverClass();

    String getUrl();

    String getUsername();

    String getPassword();
}
