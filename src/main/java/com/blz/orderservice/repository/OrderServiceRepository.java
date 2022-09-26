package com.blz.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blz.orderservice.model.OrderBookModel;

@Repository
public interface OrderServiceRepository extends JpaRepository<OrderBookModel, Long>{

	List<OrderBookModel> findByUserId(Long userId);

}
