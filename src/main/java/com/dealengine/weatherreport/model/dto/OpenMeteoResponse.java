package com.dealengine.weatherreport.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpenMeteoResponse {
    @JsonProperty("current_weather")
    private CurrentWeather currentWeather;
}
