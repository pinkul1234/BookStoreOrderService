package com.blz.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blz.orderservice.model.AddressModel;

@Repository
public interface AddressRepository extends JpaRepository<AddressModel, Long>{

}
