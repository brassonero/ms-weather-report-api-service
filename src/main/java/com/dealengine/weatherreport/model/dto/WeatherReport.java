package com.dealengine.weatherreport.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherReport {
    private TicketInfo ticketInfo;
    private WeatherData originWeather;
    private WeatherData destinationWeather;
}

