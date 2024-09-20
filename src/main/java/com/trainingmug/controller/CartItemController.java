package com.trainingmug.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trainingmug.exceptions.ResourceNotFoundException;
import com.trainingmug.model.Cart;
import com.trainingmug.model.User;
import com.trainingmug.response.ApiResponse;
import com.trainingmug.service.cart.ICartItemService;
import com.trainingmug.service.cart.ICartService;
import com.trainingmug.service.user.IUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

	private final ICartItemService cartItemService;
	private final ICartService cartService;
	private final IUserService userService;
	
	@PostMapping("/item/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long productId, @RequestParam Integer quantity){
		try {
			User user = userService.getUserById( 2L);
			Cart cart = cartService.initializeNewCart(user);
			
			cartItemService.addItemToCart(cart.getId(), productId, quantity);
			return ResponseEntity.ok(new ApiResponse("Add item success !", null));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@DeleteMapping("/{cartId}/item/{itemId}/remove")
	public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId){
		try {
			cartItemService.removeItemFromCart(cartId, itemId);
			return ResponseEntity.ok(new ApiResponse("Remove item success !", null));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PutMapping("/cart/{cartId}/item/{itemId}/update")
	public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId, @PathVariable Long itemId, @RequestParam Integer quantity){
		try {
			cartItemService.updateItemQuantity(cartId, itemId, quantity);
			return ResponseEntity.ok(new ApiResponse("Update item success !", null));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), null));
		}
	}
}
