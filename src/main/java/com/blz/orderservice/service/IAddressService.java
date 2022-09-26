package com.blz.orderservice.service;

import java.util.List;

import com.blz.orderservice.dto.AddressDTO;
import com.blz.orderservice.model.AddressModel;

public interface IAddressService {

	AddressModel inserAddress(AddressDTO addressDTO, String token);

	AddressModel updateAddress(Long addressId, AddressDTO addressDTO, String token);

	List<AddressModel>  fetchAllAddresses(String token);

	AddressModel getAddressById(Long addressId, String token);

	AddressModel deleteAddressById(Long addressId, String token);

}
