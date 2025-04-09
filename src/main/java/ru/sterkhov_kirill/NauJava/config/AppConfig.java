package ru.sterkhov_kirill.NauJava.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private static final String APP_NAME = "NauJava";
    private static final String APP_VERSION = "1.0.0";

    @PostConstruct
    public void init() {
        System.out.println("App Name: " + APP_NAME);
        System.out.println("App Version: " + APP_VERSION);
    }
}
