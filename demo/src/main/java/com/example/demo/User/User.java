package com.example.demo.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static com.example.demo.User.Constant.*;


@Entity
@Table(name = "users")
@Data
@RequiredArgsConstructor
public class User implements UserDetails {

	@Id
	@GeneratedValue
	private long id;

	@Pattern(regexp = "^[a-zA-Z]{2,20}$", message = FIRST_NAME_MESSAGE)
	private String firstName;

	@Pattern(regexp = "^[a-zA-Z]{2,20}$", message = LAST_NAME_MESSAGE)
	private String lastName;

	@Pattern(regexp = "^05[0-9]{8}$", message = PHONE_NUMBER_MESSAGE)
	private String phoneNumber;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$", message = PASSWORD_MESSAGE)
	private String password;

	@Email(message = EMAIL_MESSAGE)
	private String email;

	@Enumerated(EnumType.STRING)
	private Role role;

	public User(String firstName, String lastName, String password, String phoneNumber, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.email = email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
