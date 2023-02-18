package com.example.demo.util;

import com.example.demo.util.Responses.ValidationErrorsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

public class MakeValidationErrorsStringArray {

	public static ResponseEntity<Object> makeValidationErrorsInStringArray(Errors errors) {
		String[] errorList = new String[errors.getErrorCount()];
		for (int i = 0; i < errors.getErrorCount(); i++) {
			errorList[i] = errors.getFieldErrors().get(i).getDefaultMessage();
		}
		return new ResponseEntity<>(ValidationErrorsResponse.builder().errors(errorList).build(), HttpStatus.BAD_REQUEST);
	}

}
