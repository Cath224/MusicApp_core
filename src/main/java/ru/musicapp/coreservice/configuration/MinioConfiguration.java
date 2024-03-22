package ru.musicapp.coreservice.configuration;

import io.minio.MinioClient;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import ru.musicapp.coreservice.configuration.properties.CoreServiceMinioProperties;

@Configuration
public class MinioConfiguration {


    @Bean("minioOkHttpClient")
    public OkHttpClient minioOkHttpClient(CoreServiceMinioProperties properties) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        return builder
                .connectTimeout(properties.getTimeout())
                .readTimeout(properties.getReadTimeout())
                .build();
    }

    @Bean
    @DependsOn("minioOkHttpClient")
    public MinioClient minioClient(CoreServiceMinioProperties properties) {
        return MinioClient.builder()
                .credentials(properties.getAccessToken(), properties.getSecurityToken())
                .endpoint(properties.getUrl())
                .httpClient(minioOkHttpClient(properties))
                .build();
    }

}
