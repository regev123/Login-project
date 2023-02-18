package com.example.demo.exception;

public class NotFoundRequestHandler extends RuntimeException {

	public NotFoundRequestHandler(String message) {
		super(message);
	}
}
