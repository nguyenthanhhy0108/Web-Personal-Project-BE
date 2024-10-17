package com.wjh.configuration;

import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfiguration {

    @Value("${spring.datasource.driver-class-name}")
    @NonFinal
    private String driver;

    @Value("${spring.datasource.url}")
    @NonFinal
    private String url;

    @Value("${spring.datasource.username}")
    @NonFinal
    private String username;

    @Value("${spring.datasource.password}")
    @NonFinal
    private String password;

    @Bean
    public DataSource dataSource(){
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driver);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }
}
