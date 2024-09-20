package com.trainingmug.service.cart;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trainingmug.exceptions.ResourceNotFoundException;
import com.trainingmug.model.Cart;
import com.trainingmug.model.User;
import com.trainingmug.repository.CartIteamRepository;
import com.trainingmug.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
	
	private final CartRepository cartRepository;
	private final CartIteamRepository cartIteamRepository;
	private final AtomicLong cartIdGenerator = new AtomicLong(0);

	@Override
	public Cart getCart(Long id) {
		// TODO Auto-generated method stub
		Cart cart = cartRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Cart not found !"));
		BigDecimal totalAmount = cart.getTotalAmount();
		cart.setTotalAmount(totalAmount);
		return cartRepository.save(cart);
	}

	@Transactional
	@Override
	public void clearCart(Long id) {
		Cart cart = getCart(id);
		cartIteamRepository.deleteAllByCartId(id);
		cart.getItems().clear();
		cartRepository.deleteById(id);
		
	}

	@Override
	public BigDecimal getTotalPrice(Long id) {
		// TODO Auto-generated method stub
		Cart cart = getCart(id);
		return cart.getTotalAmount();
	}

	@Override
	public Cart initializeNewCart(User user) {
		return Optional.ofNullable(getCartByUserId(user.getId()))
				.orElseGet(() -> {
					Cart cart = new Cart();
					cart.setUser(user);
					return cartRepository.save(cart);
				});
	}

	@Override
	public Cart getCartByUserId(Long userId) {
		// TODO Auto-generated method stub
		return cartRepository.findByUserId(userId);
	}
}
