package com.example.core.multitenancy;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

@Slf4j
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        return TenantContextHolder.getDatabaseName();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }

}