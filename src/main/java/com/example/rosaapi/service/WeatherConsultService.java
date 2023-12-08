package com.example.rosaapi.service;


import com.example.rosaapi.model.dtos.WeatherDataDTO;
import com.example.rosaapi.utils.exceptions.CityInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherConsultService {
    private final String weatherApiToken = System.getenv("WEATHERAPIKEY");
    public WeatherDataDTO getWeatherData(String city) {
        try {
            String url = String.format("https://api.weatherapi.com/v1/current.json?key=%s&q=%s&aqi=no"
                    ,weatherApiToken, city);
            RestTemplate restTemplate = new RestTemplate();

            return restTemplate.getForEntity(url, WeatherDataDTO.class).getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new CityInvalidException("City not found " + e);
        } catch(HttpClientErrorException.BadRequest e ) {
            throw new CityInvalidException("Unable to understand request " + e);
        }
    }
}
