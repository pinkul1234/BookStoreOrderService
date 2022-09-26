package com.blz.orderservice.service;

import java.util.List;

import com.blz.orderservice.model.OrderBookModel;

public interface IOrderService {

	OrderBookModel placeOrder(Long cartId, Long addressId, String token);

	OrderBookModel cancelOrder(Long orderId, String token);

	List<OrderBookModel> getAllOrders(String token);

	List<OrderBookModel> getUserOrders(String token);

}
