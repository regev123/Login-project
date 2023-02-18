package com.example.demo.User;

import com.example.demo.User.Requests.LoginRequestEntity;
import com.example.demo.User.Responses.TokenResponse;
import com.example.demo.config.JwtService;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundRequestHandler;
import com.example.demo.util.Responses.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.User.Constant.*;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public ResponseEntity<Object> deleteUser(Long id) {
		userRepository.findById(id).orElseThrow(()-> new NotFoundRequestHandler(DELETE_USER_PREFIX + id + DELETE_USER_NOT_FOUND));
		userRepository.deleteById(id);
		return new ResponseEntity<Object>(MessageResponse.builder().message(DELETE_USER_PREFIX + id + DELETE_USER_SUCCEEDED).build(), HttpStatus.OK);
	}

	public TokenResponse login(LoginRequestEntity loginDetails) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDetails.getEmail(), loginDetails.getPassword()));
		User user = userRepository.findByEmail(loginDetails.getEmail()).orElseThrow(() -> new NotFoundRequestHandler(USER_NOT_FOUND));
		String jwtToken = jwtService.generateToken(user);
		return TokenResponse.builder().token(jwtToken).build();
	}

	public ResponseEntity<Object> registerUser(User user) {
		user.setRole(Role.USER);
		return registerAll(user);
	}

	public ResponseEntity<Object> registerAdmin(User user) {
		user.setRole(Role.ADMIN);
		return registerAll(user);
	}

	private ResponseEntity<Object> registerAll(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new BadRequestException(EMAIL_EXIST);
		}
		if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
			throw new BadRequestException(PHONE_NUMBER_EXIST);
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		String jwtToken = jwtService.generateToken(user);
		return new ResponseEntity<Object>(TokenResponse.builder().token(jwtToken).build(), HttpStatus.OK);
	}

	public ResponseEntity<Object> getUser(Long id) {
		return new ResponseEntity<Object>(userRepository.findById(id).orElseThrow(() -> new NotFoundRequestHandler(USER_NOT_FOUND)), HttpStatus.OK);
	}
}
