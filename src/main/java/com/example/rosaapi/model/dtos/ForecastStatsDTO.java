package com.example.rosaapi.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForecastStatsDTO {
    private Float temp_c;
    private Float feelslike_c;
    private int humidity;
    private String time;

}
