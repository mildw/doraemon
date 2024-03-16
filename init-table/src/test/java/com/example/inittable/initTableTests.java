package com.example.inittable;

import com.example.inittable.application.database.DatabaseService;
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
        System.out.println(databaseService.initTable("test1"));
    }
}
