package com.example.springcloudfunction.config.database;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.example.core.util.ObjectMapperUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.InnoDBStorageEngine;
import org.hibernate.tool.schema.Action;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = {"com.example.springcloudfunction.domain"})
public class DataSourceConfig {

    @Value("${DB_URL}")
    private String dbUrl;

    @Value("${DB_USER}")
    private String dbUsername;

    @Value("${DB_PASSWORD}")
    private String dbPassword;

    @Value("${SECRET_NAME}")
    private String secretName;
    @Bean
    public HikariConfig hikariConfig() {
        if(secretName != null && !secretName.isBlank()) {
            AwsSecrets awsSecrets = getSecretValue(secretName);
            dbUsername = awsSecrets.getUsername();
            dbPassword = awsSecrets.getPassword();
        }

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        dataSource.setMaximumPoolSize(1);
        return dataSource;
    }

    private AwsSecrets getSecretValue(String secretName) {
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().build();
        GetSecretValueRequest request = new GetSecretValueRequest().withSecretId(secretName);
        GetSecretValueResult result = client.getSecretValue(request);
        String secretString = result.getSecretString();
        if(secretString == null) {
            throw new RuntimeException();
        }

        return ObjectMapperUtils.readValue(secretString, AwsSecrets.class);
    }

    @Bean
    protected DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource());
        factory.setPackagesToScan("com.example.querydsl.domain");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setJpaProperties(getProperties());

        return factory;
    }

    private Properties getProperties() {
        Properties properties = new Properties();

        properties.put(AvailableSettings.HBM2DDL_AUTO, Action.NONE);
        properties.put(AvailableSettings.STORAGE_ENGINE, InnoDBStorageEngine.class);
        properties.put(AvailableSettings.GLOBALLY_QUOTED_IDENTIFIERS, Boolean.TRUE);
        properties.put(AvailableSettings.PHYSICAL_NAMING_STRATEGY, CamelCaseToUnderscoresNamingStrategy.class);
        properties.put(AvailableSettings.DEFAULT_BATCH_FETCH_SIZE, 500);

        return properties;
    }

    @Primary
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
