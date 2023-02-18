package com.example.demo.exception;

public class ForbiddenRequestHandler extends RuntimeException {

	public ForbiddenRequestHandler(String message) {
		super(message);
	}
}
