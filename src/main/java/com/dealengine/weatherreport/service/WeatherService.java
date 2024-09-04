package com.dealengine.weatherreport.service;

import com.dealengine.weatherreport.model.dto.WeatherData;
import com.dealengine.weatherreport.model.OpenMeteoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.cache.annotation.Cacheable;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WebClient webClient;

    @Cacheable(value = "weatherCache", key = "#lat + ',' + #lon")
    public Mono<WeatherData> getWeather(double lat, double lon) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/forecast")
                        .queryParam("latitude", lat)
                        .queryParam("longitude", lon)
                        .queryParam("current_weather", true)
                        .queryParam("temperature_unit", "celsius")
                        .build())
                .retrieve()
                .bodyToMono(OpenMeteoResponse.class)
                .map(this::convertToWeatherData);
    }

    private WeatherData convertToWeatherData(OpenMeteoResponse response) {
        if (response == null || response.getCurrentWeather() == null) {
            throw new RuntimeException("Invalid response from weather API");
        }
        return WeatherData.builder()
                .temperature(response.getCurrentWeather().getTemperature())
                .windSpeed(response.getCurrentWeather().getWindSpeed())
                .windDirection(response.getCurrentWeather().getWindDirection())
                .build();
    }
}
