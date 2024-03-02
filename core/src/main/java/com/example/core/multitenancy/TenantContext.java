package com.example.core.multitenancy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TenantContext implements Serializable {

    private Long id;
    private String databaseName;

    public TenantContext(Long id, String databaseName) {
        this.id = id;
        this.databaseName = databaseName;
    }

}