package com.example.demo.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.ZonedDateTime;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

	@ExceptionHandler(value = BadRequestException.class)
	public ResponseEntity<Object> handleBadRequestException(BadRequestException e) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		LOGGER.error("URI: " + request.getRequestURI() + "   Message: " + e.getMessage());
		ApiException apiException = new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now());
		return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = ForbiddenRequestHandler.class)
	public ResponseEntity<Object> handleForbiddenRequestHandler(ForbiddenRequestHandler e) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		LOGGER.error("URI: " + request.getRequestURI() + "   Message: " + e.getMessage());
		ApiException apiException = new ApiException(e.getMessage(), HttpStatus.FORBIDDEN, ZonedDateTime.now());
		return new ResponseEntity<>(apiException, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(value = NotFoundRequestHandler.class)
	public ResponseEntity<Object> handleNotFoundRequestHandler(NotFoundRequestHandler e) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		LOGGER.error("URI: " + request.getRequestURI() + "   Message: " + e.getMessage());
		ApiException apiException = new ApiException(e.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now());
		return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
	}

}
