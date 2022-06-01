package org.coronaVirusTracker.exceptions.handler;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import org.coronaVirusTracker.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
@RestController
public class CoronaVirusTrackerExceptionHandler extends ResponseEntityExceptionHandler {

	@SuppressWarnings("unchecked")
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		logger.debug("Handling " + ex.getMessage());
		logger.debug("Type " + ex.getClass());
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> body = new HashMap<>();
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			if (ex instanceof HttpClientErrorException) {
				HttpClientErrorException httpEx = (HttpClientErrorException) ex;
				logger.debug("status " + httpEx.getStatusCode().value());
				status = httpEx.getStatusCode();
				body = mapper.readValue(httpEx.getResponseBodyAsString(), HashMap.class);

			} else if (ex instanceof HttpServerErrorException) {
				HttpServerErrorException httpEx = (HttpServerErrorException) ex;
				status = httpEx.getStatusCode();
				body = mapper.readValue(httpEx.getResponseBodyAsString(), HashMap.class);
			}
		} catch (IOException e) {
			logger.warn("Erro ao converter exceção retornada. " + e);
		}

		logger.error("Exception body: " + body);
		return new ResponseEntity<>(exceptionResponse, status);
	}

}
