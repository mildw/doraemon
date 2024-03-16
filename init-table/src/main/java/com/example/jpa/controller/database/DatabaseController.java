package com.example.jpa.controller.database;


import com.example.jpa.application.database.DatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/database")
public class DatabaseController {

    private final DatabaseService databaseService;

    @GetMapping("/init")
    public Boolean initTable(@RequestParam String databaseName) {
        return databaseService.initTable();
    }
}
