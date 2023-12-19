package com.example.rosaapi.controllers;


import com.example.rosaapi.controllers.responses.WeatherConsultResponse;
import com.example.rosaapi.controllers.responses.WeatherForecastConsultResponse;
import com.example.rosaapi.service.WeatherConsultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/weather-consult")
@RequiredArgsConstructor
public class WeatherConsultController {

    private final WeatherConsultService weatherConsultService;


    @GetMapping("/current/{city}")
    public ResponseEntity<?> getCurrentWeather(@PathVariable("city") String city) {
        return ResponseEntity.ok(new WeatherConsultResponse(weatherConsultService.getCurrentWeatherData(city)));
    }

    @GetMapping("/forecast/{city}")
    public ResponseEntity<?> getWeatherForecast(@PathVariable("city") String city) {
        return ResponseEntity.ok(new WeatherForecastConsultResponse(
                        weatherConsultService.getWeatherForecastData(city),
                        weatherConsultService.getCurrentWeatherData(city)
                )
        );
    }

    public ResponseEntity<?> weatherAPIFallbackMethod(Throwable ex) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests were made");
    }
}
