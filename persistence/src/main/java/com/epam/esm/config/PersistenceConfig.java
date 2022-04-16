package com.epam.esm.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:db.properties")
@Profile("prod")
public class PersistenceConfig {
    private static final String DATABASE_DRIVER = "db.driver";
    private static final String DATABASE_URL = "db.url";
    private static final String DATABASE_USER_NAME = "db.user";
    private static final String DATABASE_PASSWORD = "db.password";
    private static final String DATABASE_INIT_POOL_SIZE = "db.poolSize";
    private static final String DATABASE_MAX_ACTIVE = "db.maxActive";

    private final Environment environment;

    public PersistenceConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(environment.getProperty(DATABASE_DRIVER));
        basicDataSource.setUrl(environment.getProperty(DATABASE_URL));
        basicDataSource.setUsername(environment.getProperty(DATABASE_USER_NAME));
        basicDataSource.setPassword(environment.getProperty(DATABASE_PASSWORD));
        basicDataSource.setInitialSize(Integer.parseInt(Objects.requireNonNull(environment.getProperty(DATABASE_INIT_POOL_SIZE))));
        basicDataSource.setMaxActive(Integer.parseInt(Objects.requireNonNull(environment.getProperty(DATABASE_MAX_ACTIVE))));
        return basicDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
