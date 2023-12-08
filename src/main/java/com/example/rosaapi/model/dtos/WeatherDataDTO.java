package com.example.rosaapi.model.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherDataDTO {
    private WeatherStatsDTO current;
    private WeatherLocationDTO location;
}
