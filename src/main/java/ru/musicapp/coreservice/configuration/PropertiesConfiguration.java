package ru.musicapp.coreservice.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.musicapp.coreservice.configuration.properties.CoreServiceMinioProperties;

@Configuration
@EnableConfigurationProperties({CoreServiceMinioProperties.class})
public class PropertiesConfiguration {
}
