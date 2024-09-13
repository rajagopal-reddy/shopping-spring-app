package com.trainingmug.service.product;

import java.util.List;

import com.trainingmug.dto.ProductDto;
import com.trainingmug.model.Product;
import com.trainingmug.request.AddProductRequest;
import com.trainingmug.request.UpdateProductRequest;

public interface IProductService {

	Product addProduct(AddProductRequest request);
	
	Product getProductById (Long id);
	void deleteProductById(Long id);
	Product updateProduct(UpdateProductRequest request, Long productId);
	
	List<Product> getAllProducts();
	List<Product> getProductsByCategory(String category);
	List<Product> getProductsByBrand(String brand);
	List<Product> getProductsByCategoryAndBrand(String category, String brand);
	List<Product> getProductsByName(String name);
	List<Product> getProductsByBrandAndName(String brand, String name);
	Long countProductsByBrandAndName(String brand, String name);

	ProductDto convertToDto(Product product);

	List<ProductDto> getConvertedProducts(List<Product> products);

	
}
