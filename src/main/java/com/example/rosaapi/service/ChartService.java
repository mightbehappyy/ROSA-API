package com.example.rosaapi.service;

import com.example.rosaapi.model.dtos.ForecastStatsDTO;
import io.quickchart.QuickChart;
import lombok.RequiredArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ChartService {

    private final QuickChart quickChart;
    private final int width = 500;
    private final int height = 300;
    public String getWeatherGraphic(List<ForecastStatsDTO> data){
        ArrayList<Float> temperature = new ArrayList<>();
        ArrayList<Integer> humidity = new ArrayList<>();
        ArrayList<String> timeStamp = new ArrayList<>();
        ArrayList<Float> feelsLike = new ArrayList<>();

        for (int i = 0; i < data.size(); i ++) {
            humidity.add(data.get(i).getHumidity());
            temperature.add(data.get(i).getTemp_c());
            feelsLike.add(data.get(i).getFeelslike_c());
            timeStamp.add('"'+String.valueOf(i)+ ":00"+'"');
        }

        quickChart.setWidth(width);
        quickChart.setHeight(height);
        quickChart.setBackgroundColor("rgb(255,255,255)");


        quickChart.setConfig("{" +
                "    type: 'line'," +
                "    data: {" +
                "        labels: " + timeStamp + " ,"+
                "        datasets: [" +
                "            {" +
                "                label: 'Temperatura'," +
                "                data: " + temperature + "," +
                "                fill: false" +
                "            }," +
                "            {" +
                "                label: 'Humidade'," +
                "                data: " + humidity + "," +
                "                fill: false" +
                "            }," +
                "            {" +
                "                label: 'Sensação termica'," +
                "                data: " + feelsLike + "," +
                "                fill: false" +
                "            }" +
                "        ]" +
                "    }" +
                "}");


        return quickChart.getUrl();
    }
}
