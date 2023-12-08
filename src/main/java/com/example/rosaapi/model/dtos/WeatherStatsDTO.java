package com.example.rosaapi.model.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public  class WeatherStatsDTO {
    private double temp_c;
    private WeatherIconDTO condition;
    private double wind_kph;
    private int humidity;
    private int cloud;
    private double feelslike_c;
}
