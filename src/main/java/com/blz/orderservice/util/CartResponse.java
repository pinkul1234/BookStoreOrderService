package com.blz.orderservice.util;

import com.blz.orderservice.dto.CartDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
	private int statusCode;
	private String message;
	private CartDTO object;
}
