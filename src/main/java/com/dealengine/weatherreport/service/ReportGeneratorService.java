package com.dealengine.weatherreport.service;

import com.dealengine.weatherreport.exception.AirportNotFoundException;
import com.dealengine.weatherreport.model.dto.WeatherReport;
import com.dealengine.weatherreport.model.Airport;
import com.dealengine.weatherreport.model.Ticket;
import com.dealengine.weatherreport.model.dto.TicketInfo;
import com.dealengine.weatherreport.model.dto.WeatherReportResponse;
import com.dealengine.weatherreport.repository.AirportRepository;
import com.dealengine.weatherreport.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class ReportGeneratorService {
    private final WeatherService weatherService;
    private final AirportRepository airportRepository;
    private final TicketRepository ticketRepository;

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


    public Mono<WeatherReportResponse> generateReportsForAllTickets() {
        return Flux.fromIterable(ticketRepository.findAll())
                .flatMap(this::generateReport)
                .collectList()
                .map(reports -> WeatherReportResponse.builder()
                        .reports(reports)
                        .totalReports(reports.size())
                        .build());
    }
}
