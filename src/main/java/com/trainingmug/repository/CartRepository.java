package com.trainingmug.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainingmug.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Cart findByUserId(Long userId);

}
