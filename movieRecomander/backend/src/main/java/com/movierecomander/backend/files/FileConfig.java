package com.movierecomander.backend.files;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class FileConfig {
    private String fileStorePath;

    public String getFileStorePath() {
        return fileStorePath;
    }
}
