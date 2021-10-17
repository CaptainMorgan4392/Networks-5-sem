package com.example.networks3.dao;

import com.example.networks3.domain.QueryConstants;
import com.example.networks3.dto.CoordsDto;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class GraphHopperDao {

    private final RestTemplate restTemplate;

    public GraphHopperDao() {
        this.restTemplate = new RestTemplate();
    }

    public List<CoordsDto> getCoordsOfPossibleVaries(String placeName) {
        final String url = String.format(
            "https://graphhopper.com/api/1/geocode?q=%s&locale=%s&key=%s",
            placeName,
            QueryConstants.LOCALE,
            QueryConstants.API_KEY_GRAPHHOPPER
        );
        ResponseEntity<String> jsonResponse = restTemplate.getForEntity(url, String.class);

        JsonElement hitsArray = new Gson().fromJson(jsonResponse.getBody(), JsonObject.class).get("hits");

        return new Gson().fromJson(hitsArray, new TypeToken<List<CoordsDto>>() {}.getType());
    }

}
