package com.dealengine.weatherreport.config;

import com.dealengine.weatherreport.exception.ApiCallException;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {


    @Value("${weather.api.base-url}")
    private String baseUrl;

    @Value("${webclient.connect-timeout}")
    private int connectTimeout;

    @Value("${webclient.response-timeout}")
    private int responseTimeout;

    @Value("${webclient.retry.attempts}")
    private int retryAttempts;

    @Value("${webclient.retry.delay}")
    private int retryDelay;

    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
                .responseTimeout(Duration.ofMillis(responseTimeout))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(responseTimeout, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(responseTimeout, TimeUnit.MILLISECONDS))
                );

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(baseUrl)
                .filter((request, next) -> next.exchange(request)
                        .retryWhen(Retry.fixedDelay(retryAttempts, Duration.ofSeconds(retryDelay)))
                        .onErrorResume(throwable -> {
                            throw new ApiCallException("Failed to fetch weather data", throwable);
                        }))
                .build();
    }
}
