package com.dealengine.weatherreport.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherData {
    private double temperature;
    private double windSpeed;
    private double windDirection;
}
