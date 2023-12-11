package com.example.rosaapi.controllers;


import com.example.rosaapi.controllers.responses.WeatherConsultResponse;
import com.example.rosaapi.controllers.responses.WeatherForecastConsultResponse;
import com.example.rosaapi.service.GoogleCalendarService;
import com.example.rosaapi.service.WeatherConsultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("api/weather-consult")
@RequiredArgsConstructor
public class WeatherConsultController {

    private final WeatherConsultService weatherConsultService;

    @GetMapping("/current/{city}")
    public ResponseEntity<WeatherConsultResponse> getCurrentWeather(@PathVariable("city") String city) {
        return ResponseEntity.ok(new WeatherConsultResponse(weatherConsultService.getCurrentWeatherData(city)));
    }
    @GetMapping("/forecast/{city}")
    public ResponseEntity<WeatherForecastConsultResponse> getWeatherForecast(@PathVariable("city") String city) {
        return ResponseEntity.ok(new WeatherForecastConsultResponse(
                        weatherConsultService.getWeatherForecastData(city),
                        weatherConsultService.getCurrentWeatherData(city)
                )
        );
    }
}
