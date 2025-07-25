package org.nomisng.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@Data
public class ApplicationProperties {
    public static String modulePath = System.getProperty("user.dir");
    private String databaseDir;
    private String tempDir = System.getProperty("user.dir");
    private String serverUrl;
}
