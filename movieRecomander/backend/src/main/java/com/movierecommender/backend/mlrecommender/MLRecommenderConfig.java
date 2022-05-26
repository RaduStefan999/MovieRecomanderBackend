package com.movierecommender.backend.mlrecommender;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.microservices")
public class MLRecommenderConfig {
    private String mlURI;
    private long mlTimeout;

    public String getMlURI() {
        return mlURI;
    }

    public void setMlURI(String mlURI) {
        this.mlURI = mlURI;
    }

    public long getMlTimeout() {
        return mlTimeout;
    }

    public void setMlTimeout(long mlTimeout) {
        this.mlTimeout = mlTimeout;
    }
}
