package com.example.core.multitenancy;

import org.springframework.core.NamedThreadLocal;

import java.util.Objects;

public class TenantContextHolder {

    public static final String TENANT_CONTEXT_KEY = "TenantContext";
    private static final ThreadLocal<TenantContext> tenantContextThreadLocal = new NamedThreadLocal<>(TENANT_CONTEXT_KEY);

    private TenantContextHolder() {
        throw new RuntimeException();
    }

    public static void setTenantContext(TenantContext tenantContext) {
        tenantContextThreadLocal.set(tenantContext);
    }

    public static TenantContext getTenantContext() {
        TenantContext tenantContext = tenantContextThreadLocal.get();

        if (Objects.isNull(tenantContext)) {
            throw new RuntimeException();
        }

        return tenantContext;
    }

    public static void resetTenantContext() {
        tenantContextThreadLocal.remove();
    }

    public static Long getTenantId() {
        return getTenantContext().getId();
    }

    public static String getDatabaseName() {
        return getTenantContext().getDatabaseName();
    }


}