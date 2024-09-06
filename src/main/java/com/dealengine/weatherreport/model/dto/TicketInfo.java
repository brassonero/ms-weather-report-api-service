package com.dealengine.weatherreport.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketInfo {
    private String origin;
    private String destination;
    private String airline;
    private String flightNum;
}
