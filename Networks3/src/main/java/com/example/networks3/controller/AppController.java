package com.example.networks3.controller;

import com.example.networks3.dto.FullInfoDto;
import com.example.networks3.service.AppService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping(value = "/application")
public class AppController {

    @Autowired
    private ExecutorService threadPoolExecutor;

    @SneakyThrows
    @GetMapping(value = "/getPlacesInfo/{placeName}")
    public List<FullInfoDto> getFullPlaceInfo(@PathVariable String placeName) {
        return CompletableFuture
            .supplyAsync(new AppService(placeName), threadPoolExecutor)
            .whenComplete(((responseDto1, throwable) -> {
                if (null != throwable) {
                    throw new RuntimeException(throwable);
                }
            })).get();
    }

}
