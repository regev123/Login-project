package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@ToString
public class ApiException {

	private final String message;
	private final HttpStatus httpStatus;
	private final ZonedDateTime zonedDateTime;
}
