package ru.musicapp.coreservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class ExecutorServiceConfiguration {


    @Bean(name = "coreServiceExecutorService")
    public ExecutorService executorService() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

}
