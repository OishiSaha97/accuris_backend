package com.datasoft.bkash.ea.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatasourceBean {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${slave.datasource.url}")
    private String slaveUrl;

    @Value("${slave.datasource.username}")
    private String slaveUsername;

    @Value("${slave.datasource.password}")
    private String slavePassword;

    @Value("${slave.datasource.driver-class-name}")
    private String slaveDriverClassName;

    @Value("${aml.datasource.url}")
    private String amlUrl;

    @Value("${aml.datasource.username}")
    private String amlUsername;

    @Value("${aml.datasource.password}")
    private String amlPassword;

    @Value("${aml.datasource.driver-class-name}")
    private String amlDriverClassName;

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "masterDatasource")
    public DataSource masterDatasource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }


    @Bean(name = "slaveDatasource")
    @ConfigurationProperties("slave.datasource")
    public DataSource slaveDatasource() {
        return DataSourceBuilder.create()
                .url(slaveUrl)
                .username(slaveUsername)
                .password(slavePassword)
                .driverClassName(slaveDriverClassName)
                .build();
    }

    @Bean(name = "amlDatasource")
    @ConfigurationProperties("aml.datasource")
    public DataSource amlDatasource() {
        return DataSourceBuilder.create()
                .url(amlUrl)
                .username(amlUsername)
                .password(amlPassword)
                .driverClassName(amlDriverClassName)
                .build();
    }

    @Primary
    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("masterDatasource") DataSource masterDatasource) {
        return new JdbcTemplate(masterDatasource);
    }

    @Bean(name = "template")
    public NamedParameterJdbcTemplate template(@Qualifier("masterDatasource") DataSource masterDatasource) {
        return new NamedParameterJdbcTemplate(masterDatasource);
    }

    @Bean(name = "slaveJdbcTemplate")
    public JdbcTemplate slaveJdbcTemplate(@Qualifier("slaveDatasource") DataSource slaveDatasource) {
        return new JdbcTemplate(slaveDatasource);
    }

    @Bean(name = "amlJdbcTemplate")
    public JdbcTemplate amlJdbcTemplate(@Qualifier("amlDatasource") DataSource amlDatasource) {
        return new JdbcTemplate(amlDatasource);
    }

}
