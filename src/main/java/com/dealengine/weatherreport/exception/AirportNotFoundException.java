package com.dealengine.weatherreport.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AirportNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AirportNotFoundException(String airportCode) {
        super("Airport not found with IATA code: " + airportCode);
    }
}
