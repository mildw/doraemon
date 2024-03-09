package com.example.multitenancy.config.database;

import lombok.experimental.UtilityClass;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.InnoDBStorageEngine;
import org.hibernate.tool.schema.Action;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.Properties;

@UtilityClass
public class DataSourceUtils {
    public static Properties getProperties(Environment environment) {
        Properties properties = new Properties();

        if(Arrays.asList(environment.getActiveProfiles()).contains("dv")) {
            // show sql
            properties.put(AvailableSettings.SHOW_SQL, Boolean.TRUE);
            properties.put(AvailableSettings.FORMAT_SQL, Boolean.TRUE);
        }

        properties.put(AvailableSettings.HBM2DDL_AUTO, Action.NONE);
        properties.put(AvailableSettings.STORAGE_ENGINE, InnoDBStorageEngine.class);
        properties.put(AvailableSettings.GLOBALLY_QUOTED_IDENTIFIERS, Boolean.TRUE);
        properties.put(AvailableSettings.PHYSICAL_NAMING_STRATEGY, CamelCaseToUnderscoresNamingStrategy.class);
        properties.put(AvailableSettings.DEFAULT_BATCH_FETCH_SIZE, 500);

        return properties;
    }
}
