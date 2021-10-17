package com.example.networks3.dao;

import com.example.networks3.domain.QueryConstants;
import com.example.networks3.dto.CoordsDto;
import com.example.networks3.dto.PlaceInfoDto;
import com.example.networks3.dto.PlaceXidDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class OpenTripMapDao {

    private final RestTemplate restTemplate;

    public OpenTripMapDao() {
        this.restTemplate = new RestTemplate();
    }

    public List<PlaceXidDto> getByRadius(CoordsDto coordsDto, String placeName) {
        final String url = String.format(
            "http://api.opentripmap.com/0.1/ru/places/radius?lang=%s&radius=%s&lon=%s&lat=%s&name=%s&format=json&apikey=%s",
            QueryConstants.LOCALE,
            QueryConstants.RADIUS,
            coordsDto.getPoint().getLng(),
            coordsDto.getPoint().getLat(),
            placeName,
            QueryConstants.API_KEY_OPEN_TRIP_MAP
        );
        ResponseEntity<String> jsonResponse = restTemplate.getForEntity(url, String.class);

        return new Gson().fromJson(jsonResponse.getBody(), new TypeToken<List<PlaceXidDto>>() {}.getType());
    }

    public PlaceInfoDto getByXid(PlaceXidDto placeXidDto) {
        final String url = String.format(
            "http://api.opentripmap.com/0.1/ru/places/xid/%s?lang=%s&apikey=%s",
            placeXidDto.getXid(),
            QueryConstants.LOCALE,
            QueryConstants.API_KEY_OPEN_TRIP_MAP
        );
        ResponseEntity<String> jsonResponse = restTemplate.getForEntity(url, String.class);

        return new Gson().fromJson(jsonResponse.getBody(), PlaceInfoDto.class);
    }

}
