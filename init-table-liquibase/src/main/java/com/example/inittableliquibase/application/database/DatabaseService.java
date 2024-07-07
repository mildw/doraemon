package com.example.inittableliquibase.application.database;

import com.example.core.s3.Bucket;
import com.example.core.s3.S3Utils;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseService {
    private final String tableInitFileName = "table_init.sql";
    private final String tableUpdateFileName = "table_update.sql";
    private final String s3ChangeLogPath = "database/tenant/";
    private final String changeLogPath = "classpath:/database/tenant/";

    private final ResourceLoader resourceLoader;

    public Boolean initSchemas() {
//        Bucket bucket = new Bucket("bucketName","accessKey","secretKey");
//        S3Utils.downloadToLocal(bucket,s3ChangeLogPath+tableInitFileName);
//        S3Utils.downloadToLocal(bucket,s3ChangeLogPath+tableUpdateFileName);
        boolean result = initTables("test");
//        deleteFile(changeLogPath+tableInitFileName);
//        deleteFile(changeLogPath+tableUpdateFileName);
        return result;
    }

    public Boolean updateTables(String author, String query) {
//        Bucket bucket = new Bucket("bucketName","accessKey","secretKey");
//        S3Utils.downloadToLocal(bucket,s3ChangeLogPath+tableUpdateFileName);
        int lastId = getLastChangeSetId(changeLogPath+tableUpdateFileName);
        updateSqlFile(author, query, lastId);

        List<String> schemas = List.of("test");
        boolean result = true;
        for (String schema : schemas) {
            if(!updateTable(schema)) {
                return false;
            }
        }
        if (result) {
//            S3Utils.uploadFromLocal(bucket, s3ChangeLogPath + tableUpdateFileName);
        }

        return result;
    }

    private void updateSqlFile(String author, String query, int lastId) {
        try {
            appendToFile(changeLogPath + tableUpdateFileName, lastId, author, query);
        }catch (Exception e) {
            log.error("파일 업로드 실패:{}", e.getMessage());
        }
        deleteFile(changeLogPath + tableUpdateFileName);
    }

    public Boolean initTables(String schema) {
        return executeWithResources(schema, (database) -> {
            execute(database,changeLogPath + tableInitFileName);
            execute(database,changeLogPath + tableUpdateFileName);
            return true;
        });
    }

    public Boolean updateTable(String schema) {
        return executeWithResources(schema, (database) -> {
            return execute(database, changeLogPath + tableUpdateFileName);
        });
    }

    private Boolean executeWithResources(String schema, Function<Database, Boolean> action) {
        Database database = null;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/" + schema, "root", "1111");
            database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new liquibase.database.jvm.JdbcConnection(connection));
            return action.apply(database);
        } catch (SQLException e) {
            log.error("Database connection error: " + e.getMessage(), e);
            return false;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        } finally {
            if (database != null) {
                try {
                    database.close();
                } catch (DatabaseException e) {
                    log.error("Error closing SQL connection: " + e.getMessage(), e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("Error closing SQL connection: " + e.getMessage(), e);
                }
            }
        }
    }


    public boolean execute(Database database, String changeLogFilePath) {
        try {
            Liquibase liquibase = new Liquibase(changeLogFilePath, new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts());
        }catch (Exception e) {
            log.error("리큐베이스 실패:{}", e.getMessage());
            return false;
        }
        return true;

    }

    public int getLastChangeSetId(String filePath) {
        Resource resource = resourceLoader.getResource(filePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            List<String> lines = reader.lines().collect(Collectors.toList());

            Pattern pattern = Pattern.compile("-- changeset\\s+\\w+:(\\d+)");
            int lastId = 0;

            for (String line : lines) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    int id = Integer.parseInt(matcher.group(1));
                    if (id > lastId) {
                        lastId = id;
                    }
                }
            }

            return lastId;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException();
        }
    }

    public void appendToFile(String filePath, int lastChangeSetId, String author, String content) throws IOException {
        String newSqlContent = String.format("\n-- changeset %s:%d\n%s\n", author, lastChangeSetId + 1, content);
        Resource resource = resourceLoader.getResource(filePath);
        File file = resource.getFile();
        Path path = file.toPath();
        Files.write(path, newSqlContent.getBytes(), StandardOpenOption.APPEND);

        System.out.println("Content appended to file: " + filePath);
    }

    public void deleteFile(String filePath) {
        try {
            Resource resource = resourceLoader.getResource(filePath);
            File file = resource.getFile();
            Path path = file.toPath();
            Files.deleteIfExists(path);
        }catch (Exception e) {
            log.error("로컬 파일 삭제 실패:{}",e.getMessage());
        }

    }

}
