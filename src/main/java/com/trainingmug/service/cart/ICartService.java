package com.trainingmug.service.cart;

import java.math.BigDecimal;

import com.trainingmug.model.Cart;

public interface ICartService {
	
	Cart getCart(Long id);
	void clearCart(Long id);
	BigDecimal getTotalPrice(Long id);
	Long initializeNewCart();

}
