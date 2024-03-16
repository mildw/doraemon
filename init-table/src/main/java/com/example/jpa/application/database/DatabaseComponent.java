package com.example.jpa.application.database;

import com.zaxxer.hikari.HikariConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
  
import java.sql.*;  

@Slf4j
@Component  
@RequiredArgsConstructor  
public class DatabaseComponent {  
  
    private final HikariConfig hikariConfig;  
  
    public Connection getConnection() {  
        try {  
            return createConnection();  
        } catch (SQLException e) {  
            throw new RuntimeException();
        }  
    }  
  
    public void closeConnection(Connection connection) {  
        try {  
            connection.close();  
        } catch (SQLException e) {
            throw new RuntimeException();
        }  
    }  
  
    private Connection createConnection() throws SQLException {  
        String databaseUrl = hikariConfig.getJdbcUrl().replace("/common?", "?");
        return DriverManager.getConnection(databaseUrl, hikariConfig.getUsername(), hikariConfig.getPassword());  
    }  
  
    public void changeDatabase(String databaseName, Connection connection) throws SQLException {  
        execute(String.format("USE `%s`;", databaseName), connection);  
    }  
  
    public void execute(String query, Connection connection) throws SQLException {  
        Statement statement = connection.createStatement();  
        statement.execute(query);
        log.info(query);
        statement.close();  
    }  
  
    public Integer tableCount(String databaseName, Connection connection) throws SQLException {  
        String tableCountQuery = String.format(  
                "SELECT COUNT(*) AS table_count FROM information_schema.tables WHERE table_schema = '%s';",  
                databaseName  
        );  
  
        Statement statement = connection.createStatement();  
        ResultSet resultSet = statement.executeQuery(tableCountQuery);  
        resultSet.next();  
        return resultSet.getInt("table_count");  
    }  
  
}