package com.trainingmug.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CartItemDto {

	private Long ItemId;
	private Integer quantity;
	private BigDecimal unitPrice;
	private ProductDto product;
}
