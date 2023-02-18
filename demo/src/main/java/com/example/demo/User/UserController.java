package com.example.demo.User;

import com.example.demo.User.Requests.LoginRequestEntity;
import com.example.demo.exception.ForbiddenRequestHandler;
import com.example.demo.util.Admin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.User.Constant.ADMIN_ONLY;
import static com.example.demo.util.MakeValidationErrorsStringArray.makeValidationErrorsInStringArray;

@RestController
@RequestMapping(path = "api/v1/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping(path = "registerUser")
	public ResponseEntity<Object> registerUser(@Valid @RequestBody User user, Errors errors) {
		if (errors.hasErrors()) {
			return makeValidationErrorsInStringArray(errors);
		}
		return userService.registerUser(user);
	}

	@PostMapping(path = "ADMIN/registerAdmin")
	public ResponseEntity<Object> registerAdmin(HttpServletRequest request, @Valid @RequestBody User user, Errors errors) {
		if (errors.hasErrors()) {
			return makeValidationErrorsInStringArray(errors);
		}
		if (!Admin.checkIfRequestIsAdminAutority(request, SecurityContextHolder.getContext().getAuthentication())) {
			throw new ForbiddenRequestHandler(ADMIN_ONLY);
		}
		return userService.registerAdmin(user);

	}

	@PostMapping(path = "login")
	public ResponseEntity<Object> login(@Valid @RequestBody LoginRequestEntity loginDetails, Errors errors) {
		if (errors.hasErrors()) {
			return makeValidationErrorsInStringArray(errors);
		}
		return ResponseEntity.ok(userService.login(loginDetails));
	}

	@GetMapping(path = "ADMIN/getUsers")
	public ResponseEntity<Object> getUsers(HttpServletRequest request) {
		if (!Admin.checkIfRequestIsAdminAutority(request, SecurityContextHolder.getContext().getAuthentication())) {
			throw new ForbiddenRequestHandler(ADMIN_ONLY);
		}
		return new ResponseEntity<Object>(userService.getUsers(), HttpStatus.OK);
	}

	@GetMapping(path = "ADMIN/getUser/{userId}")
	public ResponseEntity<Object> getUser(HttpServletRequest request, @PathVariable("userId") Long id) {
		if (!Admin.checkIfRequestIsAdminAutority(request, SecurityContextHolder.getContext().getAuthentication())) {
			throw new ForbiddenRequestHandler(ADMIN_ONLY);

		}
		return userService.getUser(id);
	}

	@DeleteMapping(path = "ADMIN/deleteUser/{userId}")
	public ResponseEntity<Object> deleteUser(HttpServletRequest request, @PathVariable("userId") Long id) {
		if (!Admin.checkIfRequestIsAdminAutority(request, SecurityContextHolder.getContext().getAuthentication())) {
			throw new ForbiddenRequestHandler(ADMIN_ONLY);
		}
		return userService.deleteUser(id);
	}
}
