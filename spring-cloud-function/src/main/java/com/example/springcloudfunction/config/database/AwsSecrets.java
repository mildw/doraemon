package com.example.springcloudfunction.config.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AwsSecrets {
    private String dbInstanceIdentifier;
    private String engine;
    private String resourceId;
    private String username;
    private String password;
}
