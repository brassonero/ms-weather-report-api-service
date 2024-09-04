package com.dealengine.weatherreport.service;

import com.dealengine.weatherreport.model.dto.WeatherData;
import reactor.core.publisher.Mono;

public interface WeatherService {
    Mono<WeatherData> getWeather(double lat, double lon);

}
