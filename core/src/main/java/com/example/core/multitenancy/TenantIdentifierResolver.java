package com.example.core.multitenancy;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

@Slf4j
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        try {
            return TenantContextHolder.getDatabaseName();
        } catch (Exception e) {
            return "common";
        }
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }

}