package com.blz.orderservice.util;

import com.blz.orderservice.dto.BookDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
	private int errorCode;
	private String message;
	private BookDetailsDTO object;
}
