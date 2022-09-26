package com.blz.orderservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.blz.orderservice.dto.AddressDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressModel {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;
	public String name;
	public long phoneNumber;
	public long pincode; 
	public String locality;
	public String address;
	private String city;
	private String landmark;
	private long userId;
//	@OneToOne
//	private OrderBookModel orderDetails;
	
	
	public AddressModel(AddressDTO addressDTO) {
		this.name = addressDTO.getName();
		this.phoneNumber = addressDTO.getPhoneNumber();
		this.pincode = addressDTO.getPincode();
		this.locality = addressDTO.getLocality();
		this.address = addressDTO.getAddress();
		this.city = addressDTO.getCity();
		this.landmark = addressDTO.getLandmark();
	}
}
