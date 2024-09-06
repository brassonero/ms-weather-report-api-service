package com.dealengine.weatherreport.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WeatherReportResponse {
    private List<WeatherReport> reports;
    private int totalReports;
}
