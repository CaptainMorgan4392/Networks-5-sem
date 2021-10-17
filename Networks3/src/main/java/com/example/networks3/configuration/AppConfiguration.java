package com.example.networks3.configuration;

import com.example.networks3.service.AppService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AppConfiguration {

    @Bean
    public ExecutorService threadPoolExecutor() {
        return Executors.newCachedThreadPool();
    }

    @Bean
    public AppService appService() {
        return new AppService("");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
