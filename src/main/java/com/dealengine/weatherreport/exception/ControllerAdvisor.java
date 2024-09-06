package com.dealengine.weatherreport.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Slf4j
@RestControllerAdvice
@ResponseBody
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	@ExceptionHandler(AirportNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleAirportNotFoundException(AirportNotFoundException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.NOT_FOUND.value(),
				new Date(),
				ex.getMessage(),
				request.getDescription(false));
		writeLog(message, ex);
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(TicketNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleTicketNotFoundException(TicketNotFoundException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.NOT_FOUND.value(),
				new Date(),
				ex.getMessage(),
				request.getDescription(false));
		writeLog(message, ex);
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<ErrorMessage> runtimeExceptionHandler(RuntimeException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.BAD_REQUEST.value(),
				new Date(),
				ex.getMessage(),
				request.getDescription(false)
		);
		writeLog(message, ex);
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = ApiCallException.class)
	public ResponseEntity<ErrorMessage> handleApiCallException(ApiCallException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.SERVICE_UNAVAILABLE.value(),
				new Date(),
				ex.getMessage(),
				request.getDescription(false)
		);
		writeLog(message, ex);
		return new ResponseEntity<>(message, HttpStatus.SERVICE_UNAVAILABLE);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleGenericException(Exception ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				new Date(),
				"An unexpected error occurred",
				request.getDescription(false)
		);
		writeLog(message, ex);
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private static void writeLog(ErrorMessage errorResponse, Exception exception) {
		log.error("Exception location: {}", errorResponse.getDescription());
		log.error("An exception occurred: {}", exception.getMessage());
	}
}
