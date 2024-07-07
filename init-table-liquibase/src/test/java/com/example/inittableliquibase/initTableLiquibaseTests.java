package com.example.inittableliquibase;

import com.example.inittableliquibase.application.database.DatabaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dv")
@SpringBootTest
public class initTableLiquibaseTests {

    @Autowired
    private DatabaseService databaseService;

    @Test
    public void init() {
        System.out.println(databaseService.initSchemas());
    }

    @Test
    public void update() {
        System.out.println(databaseService.updateTables("kms0428", "ALTER TABLE member ADD COLUMN status VARCHAR(255);"));
    }
}
