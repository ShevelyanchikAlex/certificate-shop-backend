package com.epam.esm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
@EnableTransactionManagement
@Import(PersistenceConfig.class)
public class ServiceConfig {
    //common regex patterns for DTO validation
    public static final String TAG_NAME_REGEX_PATTERN = "^(#[A-Za-z0-9_]{1,20})$";
    public static final String GIFT_CERTIFICATE_NAME_REGEX_PATTERN = "^([A-Za-z ]{1,45})$";
    public static final String GIFT_CERTIFICATE_DESCRIPTION_REGEX_PATTERN = "^([A-Za-z ]{1,200})$";

    private final DataSource dataSource;

    @Autowired
    public ServiceConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

}
