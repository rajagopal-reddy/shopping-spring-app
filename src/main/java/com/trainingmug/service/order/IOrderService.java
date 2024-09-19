package com.trainingmug.service.order;

import java.util.List;

import com.trainingmug.model.Order;

public interface IOrderService {

	Order placeOrder(Long userId);
	Order getOrder(Long orderId);
	List<Order> getUserOrder(Long userId);
}
