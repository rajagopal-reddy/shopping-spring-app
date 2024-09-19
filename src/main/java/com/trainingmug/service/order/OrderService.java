package com.trainingmug.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.trainingmug.enums.OrderStatus;
import com.trainingmug.exceptions.ResourceNotFoundException;
import com.trainingmug.model.Cart;
import com.trainingmug.model.Order;
import com.trainingmug.model.OrderItem;
import com.trainingmug.model.Product;
import com.trainingmug.repository.OrderRepository;
import com.trainingmug.repository.ProductRepository;
import com.trainingmug.service.cart.ICartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final ICartService cartService;

	@Override
	public Order placeOrder(Long userId) {

		Cart cart = cartService.getCartByUserId(userId);
		Order order = createOrder(cart);
		List<OrderItem> orderItemList = createOrderItems(order, cart);
		
		order.setOrderItems(new HashSet<>(orderItemList));
		order.setTotalAmount(calculateTotalAmount(orderItemList));
		Order savedOrder = orderRepository.save(order);
		cartService.clearCart(cart.getId());
		return savedOrder;
	}
	
	private Order createOrder(Cart cart) {
		Order order = new Order();
		order.setUser(cart.getUser());
		order.setOrderStatus(OrderStatus.PENDING);
		order.setOrderDate(LocalDate.now());
		return order;
	
	}
	
	
	
	
	
	private List<OrderItem> createOrderItems(Order order, Cart cart){
		return cart.getItems()
				.stream()
				.map(cartItem -> {
					Product product = cartItem.getProduct();
					product.setInventory(product.getInventory() - cartItem.getQuantity());
					productRepository.save(product);
					return new OrderItem(
							order,
							product,
							cartItem.getQuantity(),
							cartItem.getUnitPrice()
							);
				})
				.toList();
	}
	
	
	
	
	
	
	private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
		return orderItemList.stream()
				.map(item -> item.getPrice()
						.multiply(new BigDecimal(item.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal :: add);
	}
	
	
	
	

	@Override
	public Order getOrder(Long orderId) {
		// TODO Auto-generated method stub
		return orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));
	}
	
	@Override
	public List<Order> getUserOrder(Long userId){
		return orderRepository.findByUserId(userId);
		
	}
	
	
	
	
	
	
	
}
