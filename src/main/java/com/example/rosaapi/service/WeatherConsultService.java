package com.example.rosaapi.service;


import com.example.rosaapi.model.dtos.ForecastDataDTO;
import com.example.rosaapi.model.dtos.ForecastStatsDTO;
import com.example.rosaapi.model.dtos.ForecastdayDTO;
import com.example.rosaapi.model.dtos.WeatherDataDTO;
import com.example.rosaapi.utils.exceptions.CityInvalidException;
import io.quickchart.QuickChart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WeatherConsultService {
    private final String weatherApiToken = System.getenv("WEATHERAPIKEY");
    public WeatherDataDTO getCurrentWeatherData(String city) {
        try {
            String url = String.format("https://api.weatherapi.com/v1/current.json?key=%s&q=%s&aqi=no"
                    ,weatherApiToken, city);
            RestTemplate restTemplate = new RestTemplate();

            return restTemplate.getForEntity(url, WeatherDataDTO.class).getBody();
        }
        catch(HttpClientErrorException.BadRequest e ) {
            throw new CityInvalidException("Unable to understand city's name");
        }
    }

    public ForecastDataDTO getWeatherForecastData(String city) {
        try{
            String url = String.format("https://api.weatherapi.com/v1/forecast.json?key=%s&q=%s&days=1&aqi=no&alerts=no"
                    ,weatherApiToken, city);
            RestTemplate restTemplate = new RestTemplate();

            ForecastdayDTO forecastdayDTO = Objects.requireNonNull(restTemplate
                            .getForEntity(url, ForecastDataDTO.class)
                            .getBody())
                    .getForecast()
                    .getForecastday().get(0);

            List<ForecastStatsDTO> data = new ArrayList<>(forecastdayDTO.getHour());
            ChartService chartService = new ChartService(new QuickChart());
            String graphURL = chartService.getWeatherGraphic(data);

            ForecastDataDTO forecastData = restTemplate.getForEntity(url, ForecastDataDTO.class).getBody();
            Objects.requireNonNull(forecastData).setUrl(graphURL);

            return forecastData;
        }  catch (HttpClientErrorException.BadRequest e) {
            throw new CityInvalidException("Unable to understand city's name");
        }
    }
}