package com.dealengine.weatherreport.service.impl;

import com.dealengine.weatherreport.exception.AirportNotFoundException;
import com.dealengine.weatherreport.model.dto.WeatherReport;
import com.dealengine.weatherreport.model.entity.Airport;
import com.dealengine.weatherreport.model.entity.Ticket;
import com.dealengine.weatherreport.model.dto.TicketInfo;
import com.dealengine.weatherreport.model.dto.WeatherReportResponse;
import com.dealengine.weatherreport.repository.AirportRepository;
import com.dealengine.weatherreport.repository.TicketRepository;
import com.dealengine.weatherreport.service.ReportGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class ReportGeneratorServiceImpl implements ReportGeneratorService {
    private final WeatherServiceImpl weatherService;
    private final AirportRepository airportRepository;
    private final TicketRepository ticketRepository;

    @Override
    public Mono<WeatherReport> generateReport(Ticket ticket) {
        return Mono.fromCallable(() -> {
            Airport origin = airportRepository.findById(ticket.getOrigin())
                    .orElseThrow(() -> new AirportNotFoundException(ticket.getOrigin()));
            Airport destination = airportRepository.findById(ticket.getDestination())
                    .orElseThrow(() -> new AirportNotFoundException(ticket.getDestination()));

            return Mono.zip(
                    weatherService.getWeather(origin.getLatitude(), origin.getLongitude()),
                    weatherService.getWeather(destination.getLatitude(), destination.getLongitude()),
                    (originWeather, destWeather) -> WeatherReport.builder()
                            .ticketInfo(TicketInfo.builder()
                                    .origin(ticket.getOrigin())
                                    .destination(ticket.getDestination())
                                    .airline(ticket.getAirline())
                                    .flightNum(ticket.getFlightNum())
                                    .build())
                            .originWeather(originWeather)
                            .destinationWeather(destWeather)
                            .build()
            );
        }).flatMap(mono -> mono).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<WeatherReportResponse> generateReportsForAllTickets() {
        return Flux.fromIterable(ticketRepository.findAll())
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(this::generateReport)
                .sequential()
                .collectList()
                .map(reports -> WeatherReportResponse.builder()
                        .reports(reports)
                        .totalReports(reports.size())
                        .build());
    }
}
