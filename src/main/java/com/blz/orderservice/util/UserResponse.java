package com.blz.orderservice.util;

import com.blz.orderservice.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	private int errorCode;
	private String message;
	private UserDTO object;
	
	public UserResponse(int errorCode, String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
	}
}
