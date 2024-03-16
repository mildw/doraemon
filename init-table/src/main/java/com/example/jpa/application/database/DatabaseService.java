package com.example.jpa.application.database;

import com.example.core.multitenancy.TenantContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseService {

    private final DatabaseComponent databaseComponent;

    public Boolean initTable() {
        try {
            initTenantTable();
        }catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    private Boolean initTenantTable() throws IOException, SQLException {
        String databaseName = TenantContextHolder.getDatabaseName();

        Connection connection = databaseComponent.getConnection();
        databaseComponent.changeDatabase(databaseName, connection);

        Integer tableCount = databaseComponent.tableCount(databaseName, connection);

        if (tableCount > 0) {
            return true;
        }

        List<String> queries;
        String path = "database/tenant/init_table.sql";
        ClassPathResource resource = new ClassPathResource(path);
        String query = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        queries = List.of(query.split(";"));
        Boolean isSucceed = executeQuery(connection, queries);

        databaseComponent.closeConnection(connection);
        return isSucceed;

    }

    private Boolean executeQuery(Connection connection, List<String> queries) throws SQLException {
        try {
            for (String query : queries) {
                if (query.isBlank()) {
                    continue;
                }
                databaseComponent.execute(query, connection);
            }
        }catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

}
