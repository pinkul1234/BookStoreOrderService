package com.blz.orderservice.dto;

import javax.persistence.Lob;

import lombok.Data;


@Data
public class BookDetailsDTO {
	private long bookId;
    private String bookName;
    private String bookAuthor;
    private String bookDescription;  
    @Lob
	private byte[] bookLogo;
    private int bookPrice;
    private int bookQuantity;
}
