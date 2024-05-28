package ru.musicapp.coreservice.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.musicapp.coreservice.configuration.properties.CoreServiceMinioProperties;
import ru.musicapp.coreservice.configuration.properties.PredictionServiceClientProperties;

@Configuration
@EnableConfigurationProperties({CoreServiceMinioProperties.class, PredictionServiceClientProperties.class})
public class PropertiesConfiguration {
}
