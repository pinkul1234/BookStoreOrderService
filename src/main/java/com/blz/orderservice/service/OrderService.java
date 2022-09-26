package com.blz.orderservice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.blz.orderservice.exception.OrderDetailsNotFoundException;
import com.blz.orderservice.model.AddressModel;
import com.blz.orderservice.model.OrderBookModel;
import com.blz.orderservice.repository.AddressRepository;
import com.blz.orderservice.repository.OrderServiceRepository;
import com.blz.orderservice.util.BookResponse;
import com.blz.orderservice.util.CartResponse;
import com.blz.orderservice.util.TokenUtil;
import com.blz.orderservice.util.UserResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService implements IOrderService{
	@Autowired
	OrderServiceRepository orderServiceRepository;
	@Autowired
	TokenUtil tokenUtil;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	MailService mailService;

	@Override
	public OrderBookModel placeOrder(Long cartId, Long addressId, String token) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://USER-SERVICE:8082/user/validate/" + userId, UserResponse.class);
		if (isUserPresent.getErrorCode() == 200) {
			CartResponse isCartPresent = restTemplate.getForObject("http://CART-SERVICE:8090/cartservice/getcart/" + cartId, CartResponse.class);
			if (isUserPresent.getErrorCode() == 200) {
				if (isUserPresent.getObject().getUserId() == isCartPresent.getObject().getUserId()) {
					Optional<AddressModel> isAddressPresent = addressRepository.findById(addressId);
					OrderBookModel order = new OrderBookModel();
					order.setOrderDate(LocalDateTime.now());
					order.setBookId(isCartPresent.getObject().getBookId());
					order.setPrice(isCartPresent.getObject().getTotalPrice());
					order.setQuantity(isCartPresent.getObject().getQuantity());
					order.setUserId(isCartPresent.getObject().getUserId());
					order.setCartId(cartId);
					order.setCancel(false);
					if (isAddressPresent.isPresent()) {
						if (isAddressPresent.get().getUserId() == isUserPresent.getObject().getUserId()) {
							order.setAddress(isAddressPresent.get());
						}
						else {
							throw new OrderDetailsNotFoundException(500, "Address UserId and User Id Not Match");
						}
					}
					else {
						throw new OrderDetailsNotFoundException(500, "Address Not Found This User Id");
					}
					orderServiceRepository.save(order);
					String body = "Your Order Placer with Order Id is :" +order.getOrderId();
					String subject = "Order Successfully Placed";
					mailService.send(isUserPresent.getObject().getEmailId(), subject, body);
					CartResponse isPresent = restTemplate.getForObject("http://CART-SERVICE:8090/cartservice/delete/" + order.getCartId(), CartResponse.class);
					return order;
				}
				throw new OrderDetailsNotFoundException(500, "No Cart Found For This User Id");			
			}
			throw new OrderDetailsNotFoundException(500, "No Cart Found For This User Id");
		}
		return null;
	}

	@Override
	public OrderBookModel cancelOrder(Long orderId, String token) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://USER-SERVICE:8082/user/validate/" + userId, UserResponse.class);
		if (isUserPresent.getErrorCode() == 200) {
			Optional<OrderBookModel> isOrderPresent = orderServiceRepository.findById(orderId);
			if (isOrderPresent.isPresent()) {
				isOrderPresent.get().setCancel(true);
				BookResponse isBookIdPresent = restTemplate.getForObject("http://BOOK-SERVICE:8089/bookdetails/updatequantity/" +
						isOrderPresent.get().getBookId() +"/"+ isOrderPresent.get().getQuantity(), BookResponse.class);
				orderServiceRepository.delete(isOrderPresent.get());
				return isOrderPresent.get();
			}
			throw new OrderDetailsNotFoundException(500, "No Order Found For This Order Id");
		}
		throw new OrderDetailsNotFoundException(500, "No Order Found For This User Id");
	}

	@Override
	public List<OrderBookModel> getAllOrders(String token) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://USER-SERVICE:8082/user/validate/" + userId, UserResponse.class);
		if (isUserPresent.getErrorCode() == 200) {
			List<OrderBookModel> isOrdersPresent = orderServiceRepository.findAll();
			if (isOrdersPresent.size()>0) {
				return isOrdersPresent;
			}
			throw new OrderDetailsNotFoundException(500, "No Orders Found");
		}
		throw new OrderDetailsNotFoundException(500, "Invalid User");
	}

	@Override
	public List<OrderBookModel> getUserOrders(String token) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://USER-SERVICE:8082/user/validate/" + userId, UserResponse.class);
		if (isUserPresent.getErrorCode() == 200) {
			List<OrderBookModel> isOrdersPresent = orderServiceRepository.findByUserId(userId);
			if (isOrdersPresent.size()>0) {
				return isOrdersPresent;
			}
		}
		throw new OrderDetailsNotFoundException(500, "Invalid User");
	}

}
