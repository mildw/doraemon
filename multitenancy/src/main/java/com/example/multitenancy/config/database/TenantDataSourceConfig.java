package com.example.multitenancy.config.database;

import com.example.core.multitenancy.TenantConnectionProvider;
import com.example.core.multitenancy.TenantIdentifierResolver;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.InnoDBStorageEngine;
import org.hibernate.tool.schema.Action;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(
        basePackages = "com.example.multitenancy.domain.tenant",
        entityManagerFactoryRef = "tenantEntityManagerFactory",
        transactionManagerRef = "tenantTransactionManager"
)
public class TenantDataSourceConfig {

    private final ConfigurableEnvironment environment;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari.tenant")
    public HikariConfig tenantHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    protected DataSource tenantDataSource() {
        return new HikariDataSource(tenantHikariConfig());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean tenantEntityManagerFactory(DataSource tenantDataSource) {
        Properties properties = getProperties(environment);
        setMultiTenantSetting(tenantDataSource, properties);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(tenantDataSource);
        factory.setPackagesToScan("com.example.multitenancy.domain.tenant");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setJpaProperties(properties);
        factory.setPersistenceUnitName("tenant");
        return factory;
    }


    private Properties getProperties(Environment environment) {
        Properties properties = new Properties();

        if(Arrays.asList(environment.getActiveProfiles()).contains("dv")) {
            // show sql
            properties.put(AvailableSettings.SHOW_SQL, Boolean.TRUE);
            properties.put(AvailableSettings.FORMAT_SQL, Boolean.TRUE);
        }

        properties.put(AvailableSettings.HBM2DDL_AUTO, Action.NONE);
        properties.put(AvailableSettings.STORAGE_ENGINE, InnoDBStorageEngine.class);
        properties.put(AvailableSettings.GLOBALLY_QUOTED_IDENTIFIERS, Boolean.TRUE);
        properties.put(AvailableSettings.PHYSICAL_NAMING_STRATEGY, CamelCaseToUnderscoresNamingStrategy.class);
        properties.put(AvailableSettings.DEFAULT_BATCH_FETCH_SIZE, 500);

        return properties;
    }

    private void setMultiTenantSetting(DataSource tenantDataSource, Properties properties) {
        properties.put(AvailableSettings.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
        properties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, new TenantConnectionProvider(tenantDataSource));
        properties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, new TenantIdentifierResolver());
    }

    @Bean
    public PlatformTransactionManager tenantTransactionManager(EntityManagerFactory tenantEntityManagerFactory) {
        return new JpaTransactionManager(tenantEntityManagerFactory);
    }

}