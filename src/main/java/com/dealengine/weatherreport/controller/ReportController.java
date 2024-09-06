package com.dealengine.weatherreport.controller;

import com.dealengine.weatherreport.model.dto.WeatherReportResponse;
import com.dealengine.weatherreport.service.ReportGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("${weather.api.base-path}" )
public class ReportController {
    private final ReportGeneratorService reportGeneratorService;

    @GetMapping("${weather.api.generate-report-endpoint}")
    public Mono<WeatherReportResponse> generateReport() {
        return reportGeneratorService.generateReportsForAllTickets();
    }
}
