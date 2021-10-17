package com.example.networks3.service;

import com.example.networks3.dao.GraphHopperDao;
import com.example.networks3.dao.OpenTripMapDao;
import com.example.networks3.dao.OpenWeatherDao;
import com.example.networks3.dto.CoordsDto;
import com.example.networks3.dto.PlaceInfoDto;
import com.example.networks3.dto.PlaceXidDto;
import com.example.networks3.dto.FullInfoDto;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppService implements Supplier<List<FullInfoDto>> {

    private final String placeName;

    private final GraphHopperDao graphHopperDao;

    private final OpenTripMapDao openTripMapDao;

    private final OpenWeatherDao openWeatherDao;

    public AppService(String placeName) {
        this.placeName = placeName;
        this.graphHopperDao = new GraphHopperDao();
        this.openTripMapDao = new OpenTripMapDao();
        this.openWeatherDao = new OpenWeatherDao();
    }

    @Override
    public List<FullInfoDto> get() {
        Stream<PlaceXidDto> placeXidDtoStream = Stream.<PlaceXidDto>builder().build();

        List<CoordsDto> coordsDtos = getCoordsOfPossibleVaries(placeName);

        for (CoordsDto coordsDto : coordsDtos) {
            placeXidDtoStream = Stream.concat(placeXidDtoStream, getListOfPlacesByRadius(coordsDto, placeName).stream());
        }
        placeXidDtoStream = placeXidDtoStream.distinct();

        return placeXidDtoStream
            .map(this::getPlaceByXid)
            .map(this::getInfoWithWeather)
            .collect(Collectors.toList());
    }

    private List<CoordsDto> getCoordsOfPossibleVaries(String placeName) {
        return graphHopperDao.getCoordsOfPossibleVaries(placeName);
    }

    private List<PlaceXidDto> getListOfPlacesByRadius(CoordsDto coordsDto, String placeName) {
        return openTripMapDao.getByRadius(coordsDto, placeName);
    }

    private PlaceInfoDto getPlaceByXid(PlaceXidDto placeRawDto) {
        return openTripMapDao.getByXid(placeRawDto);
    }

    private FullInfoDto getInfoWithWeather(PlaceInfoDto placeInfoDto) {
        return openWeatherDao.addWeather(placeInfoDto);
    }

}
