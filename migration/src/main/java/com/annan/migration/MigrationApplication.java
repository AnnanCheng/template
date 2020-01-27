package com.annan.migration;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MigrationApplication implements CommandLineRunner {

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    public static void main(String[] args) {
        SpringApplication.run(MigrationApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Flyway flyway = Flyway.configure().dataSource(url, username, password).load();
        flyway.migrate();
    }
}
