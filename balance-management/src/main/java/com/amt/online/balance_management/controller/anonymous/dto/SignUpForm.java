package com.amt.online.balance_management.controller.anonymous.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpForm {
	
	@NotBlank(message ="Please enter member name.")
	private String name;
	@NotBlank(message ="Please enter email for login.")
	private String username;
	@NotBlank(message ="Please enter password.")
	private String password;

}
