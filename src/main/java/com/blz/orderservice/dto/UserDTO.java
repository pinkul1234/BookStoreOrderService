package com.blz.orderservice.dto;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Lob;

import lombok.Data;

@Data
public class UserDTO {
	private long userId;
	private String firstName;
	private String lastName;
	private String kyc;
	private String dob;
	private String emailId;
	private String password;
	private String phoneno;
	private LocalDateTime registerDate;
	private LocalDateTime updatedDate;
	private boolean verify;
	private long otp;
	private Date purchaseDate;
	private Date expiryDate;
	@Lob
	private byte[] profilepic;
}
