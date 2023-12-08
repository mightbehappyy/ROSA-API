package com.example.rosaapi.controllers.responses;



import com.example.rosaapi.model.dtos.ForecastDataDTO;
import com.example.rosaapi.model.dtos.WeatherDataDTO;
import lombok.Getter;

@Getter
public class WeatherForecastConsultResponse {

    private final String name;
    private final String region;
    private final String country;
    private final String url;
    public WeatherForecastConsultResponse(ForecastDataDTO weatherForecastDTO, WeatherDataDTO weatherData) {
        this.url = weatherForecastDTO.getUrl();
        this.name = weatherData.getLocation().getName();
        this.region = weatherData.getLocation().getRegion();
        this.country = weatherData.getLocation().getCountry();

    }
}
