package com.dealengine.weatherreport.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentWeather {
    private double temperature;
    @JsonProperty("windspeed")
    private double windSpeed;
    @JsonProperty("winddirection")
    private double windDirection;
}
