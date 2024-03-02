package com.example.core.s3;

import com.example.core.multitenancy.TenantContextHolder;

public class S3FilePath {

    public static String getTempFileKey(String fileName) {
        return new StringBuilder().append("temp").append("/").append(fileName).toString();
    }

}