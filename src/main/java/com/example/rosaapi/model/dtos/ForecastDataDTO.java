package com.example.rosaapi.model.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForecastDataDTO {
    private ForecastDTO forecast;
    private String url;
}
