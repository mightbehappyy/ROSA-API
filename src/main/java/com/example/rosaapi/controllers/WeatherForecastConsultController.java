package com.example.rosaapi.controllers;

import com.example.rosaapi.controllers.responses.WeatherForecastConsultResponse;
import com.example.rosaapi.service.WeatherConsultService;
import com.example.rosaapi.service.WeatherForecastConsultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/graphic-weather-consult")
@RequiredArgsConstructor

public class WeatherForecastConsultController {

    private final WeatherForecastConsultService weatherGraphicConsultService;
    private final WeatherConsultService weatherConsultService;
    @GetMapping("{city}")
    public ResponseEntity<WeatherForecastConsultResponse> getWeatherGraphic(@PathVariable("city") String city) {

        return ResponseEntity.ok(new WeatherForecastConsultResponse(
                weatherGraphicConsultService.getForecastData(city),
                weatherConsultService.getWeatherData(city)
                )
        );
    }
}
