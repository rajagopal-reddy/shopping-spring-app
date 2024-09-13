package com.trainingmug.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainingmug.model.CartItem;

public interface CartIteamRepository extends JpaRepository<CartItem, Long>{

	void deleteAllByCartId(Long id);

}
