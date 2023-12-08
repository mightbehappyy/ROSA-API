package com.example.rosaapi.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherLocationDTO {
    private String name;
    private String region;
    private String country;
    private String localtime;
}
