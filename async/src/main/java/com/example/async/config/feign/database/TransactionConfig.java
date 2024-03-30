package com.example.async.config.feign.database;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TransactionConfig {

    @Primary
    @Bean
    public ChainedTransactionManager transactionManager(
            @Qualifier("commonTransactionManager") PlatformTransactionManager commonTransactionManager,
            @Qualifier("tenantTransactionManager") PlatformTransactionManager tenantTransactionManager) {

        return new ChainedTransactionManager(commonTransactionManager, tenantTransactionManager);

    }

}