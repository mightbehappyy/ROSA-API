package com.example.rosaapi.controllers.responses;

import com.example.rosaapi.model.dtos.WeatherDataDTO;
import lombok.Getter;

@Getter
public class WeatherConsultResponse {

    private final String name;
    private final String region;
    private final String country;
    private final String localTime;
    private final double temperature;
    private final String text;
    private final String icon;
    private final double windSpeed;
    private final int humidity;
    private final int cloud;
    private final double feelsLike;

    public WeatherConsultResponse(WeatherDataDTO weatherDataDTO) {
        this.name = weatherDataDTO.getLocation().getName();
        this.region = weatherDataDTO.getLocation().getRegion();
        this.country = weatherDataDTO.getLocation().getCountry();
        this.localTime = weatherDataDTO.getLocation().getLocaltime();
        this.temperature = weatherDataDTO.getCurrent().getTemp_c();
        this.windSpeed = weatherDataDTO.getCurrent().getWind_kph();
        this.humidity = weatherDataDTO.getCurrent().getHumidity();
        this.cloud = weatherDataDTO.getCurrent().getCloud();
        this.feelsLike = weatherDataDTO.getCurrent().getFeelslike_c();
        this.text = weatherDataDTO.getCurrent().getCondition().getText();
        this.icon = weatherDataDTO.getCurrent().getCondition().getIcon();

    }
}
