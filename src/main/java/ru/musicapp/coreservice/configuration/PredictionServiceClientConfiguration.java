package ru.musicapp.coreservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.musicapp.coreservice.configuration.properties.PredictionServiceClientProperties;

@Configuration
public class PredictionServiceClientConfiguration {


    @Bean("predictionServiceClient")
    public RestClient restClient(PredictionServiceClientProperties properties) {
        return RestClient.builder()
                .baseUrl(properties.getUrl())
                .build();
    }


}
