package com.example.networks3.dao;

import com.example.networks3.domain.QueryConstants;
import com.example.networks3.dto.FullInfoDto;
import com.example.networks3.dto.PlaceInfoDto;
import com.example.networks3.dto.WeatherDto;
import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class OpenWeatherDao {

    private final RestTemplate restTemplate;

    public OpenWeatherDao() {
        this.restTemplate = new RestTemplate();
    }

    public FullInfoDto addWeather(PlaceInfoDto placeInfoDto) {
        final String url = String.format(
            "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s",
            placeInfoDto.getPoint().getLat(),
            placeInfoDto.getPoint().getLon(),
            QueryConstants.API_KEY_OPEN_WEATHER
        );
        ResponseEntity<String> jsonResponse = restTemplate.getForEntity(url, String.class);

        WeatherDto weatherDto = new Gson().fromJson(jsonResponse.getBody(), WeatherDto.class);
        return new FullInfoDto(placeInfoDto, weatherDto);
    }

}
