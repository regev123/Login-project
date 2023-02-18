package com.example.demo.User.Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.demo.User.Constant.EMAIL_MESSAGE;
import static com.example.demo.User.Constant.PASSWORD_MESSAGE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestEntity {

	@Email(message = EMAIL_MESSAGE)
	private String email;
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$", message = PASSWORD_MESSAGE)
	private String password;
}
