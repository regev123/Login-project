package com.example.demo.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public class Admin {

	public static boolean checkIfRequestIsAdminAutority(HttpServletRequest request, Authentication auth) {
		if (request.getRequestURI().contains("ADMIN")) {
			if (auth.getAuthorities().toString().contains("ADMIN")) {
				return true;
			}
		}
		return false;
	}
}
