package com.example.jpa;

import com.example.core.multitenancy.TenantContext;
import com.example.core.multitenancy.TenantContextHolder;
import com.example.jpa.application.database.DatabaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dv")
@SpringBootTest
public class initTableTests {

    @Autowired
    private DatabaseService databaseService;

    @Test
    public void test() {
        TenantContextHolder.setTenantContext(new TenantContext(null, "test1"));
        System.out.println(databaseService.initTable());
    }
}
