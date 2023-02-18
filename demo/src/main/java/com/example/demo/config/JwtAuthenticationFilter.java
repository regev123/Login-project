package com.example.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String email;
		//if the authorization header does not exist on the request or is not starting with Bearer then early return
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		//the beginning of the jwt string starts with "Bearer {jwt}" so the actual token starting from index 7
		jwt = authHeader.substring(7);
		//extract the decrypted email that is in the jwt encrypted string
		email = jwtService.extractEmail(jwt);
		//if the email exists and the user is not Authentication
		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			//load the user by email and get the user details from the database
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
			//checking if the user and the token are valid or not
			if (jwtService.isTokenValid(jwt, userDetails)) {
				//create an object of type username password authentication and pass user details, credentials, and authorities as parameter
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				//extend this authentication token with the details of the request and then update the authentication token
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				//update the current user authentication token
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}
}
