package com.dealengine.weatherreport.service;

import com.dealengine.weatherreport.model.dto.WeatherReport;
import com.dealengine.weatherreport.model.dto.WeatherReportResponse;
import com.dealengine.weatherreport.model.entity.Ticket;
import reactor.core.publisher.Mono;

public interface ReportGeneratorService {
    Mono<WeatherReport> generateReport(Ticket ticket);

    Mono<WeatherReportResponse> generateReportsForAllTickets();
}
