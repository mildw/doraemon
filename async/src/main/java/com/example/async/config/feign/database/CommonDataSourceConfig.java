package com.example.async.config.feign.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(
        basePackages = "com.example.async.domain.common"
)
@Slf4j
public class CommonDataSourceConfig {

    private final ConfigurableEnvironment environment;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari.common")
    public HikariConfig commonHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    protected DataSource commonDataSource() {
        return new HikariDataSource(commonHikariConfig());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(commonDataSource());
        factory.setPackagesToScan("com.example.async.domain.common");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setJpaProperties(DataSourceUtils.getProperties(environment));
        factory.setPersistenceUnitName("common");
        return factory;
    }

    @Bean
    public PlatformTransactionManager commonTransactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}