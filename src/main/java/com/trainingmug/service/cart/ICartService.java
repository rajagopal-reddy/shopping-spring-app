package com.trainingmug.service.cart;

import java.math.BigDecimal;

import com.trainingmug.model.Cart;
import com.trainingmug.model.User;

public interface ICartService {
	
	Cart getCart(Long id);
	void clearCart(Long id);
	BigDecimal getTotalPrice(Long id);
	Cart getCartByUserId(Long userId);
	Cart initializeNewCart(User user);

}
