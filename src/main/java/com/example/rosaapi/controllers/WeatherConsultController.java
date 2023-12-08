package com.example.rosaapi.controllers;


import com.example.rosaapi.controllers.responses.WeatherConsultResponse;
import com.example.rosaapi.service.WeatherConsultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/weather-consult")
@RequiredArgsConstructor
public class WeatherConsultController {

    private final WeatherConsultService weatherConsultService;

    @GetMapping("{city}")
    public ResponseEntity<WeatherConsultResponse> getCurrentWeather(@PathVariable("city") String city) {
        return ResponseEntity.ok(new WeatherConsultResponse(weatherConsultService.getWeatherData(city)));
    }
}
