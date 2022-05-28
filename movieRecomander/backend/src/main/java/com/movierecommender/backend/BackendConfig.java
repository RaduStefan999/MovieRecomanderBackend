package com.movierecommender.backend;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.backend")
public class BackendConfig {
    private String selfURI;

    public BackendConfig() {
    }

    public String getSelfURI() {
        return selfURI;
    }

    public void setSelfURI(String selfURI) {
        this.selfURI = selfURI;
    }
}