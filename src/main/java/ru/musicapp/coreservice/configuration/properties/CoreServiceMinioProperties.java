package ru.musicapp.coreservice.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@ConfigurationProperties(prefix = "minio")
public class CoreServiceMinioProperties {

    private String url;
    private String accessToken;
    private String securityToken;
    private Duration timeout;
    private Duration readTimeout;


}
