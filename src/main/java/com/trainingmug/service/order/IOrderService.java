package com.trainingmug.service.order;

import java.util.List;

import com.trainingmug.dto.OrderDto;
import com.trainingmug.model.Order;

public interface IOrderService {

	Order placeOrder(Long userId);
	OrderDto getOrder(Long orderId);
	List<OrderDto> getUserOrder(Long userId);
	OrderDto convertToDto(Order order);
}
