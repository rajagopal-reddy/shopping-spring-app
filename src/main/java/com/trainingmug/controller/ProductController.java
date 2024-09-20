package com.trainingmug.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trainingmug.dto.ProductDto;
import com.trainingmug.exceptions.AlreadyExistsExceptoin;
import com.trainingmug.exceptions.ResourceNotFoundException;
import com.trainingmug.model.Product;
import com.trainingmug.request.AddProductRequest;
import com.trainingmug.request.UpdateProductRequest;
import com.trainingmug.response.ApiResponse;
import com.trainingmug.service.product.IProductService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
	
	private final IProductService productService;
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		List<ProductDto> convertedProduct = productService.getConvertedProducts(products);
		return ResponseEntity.ok(new ApiResponse("All Products found !", convertedProduct));
	}
	
	@GetMapping("/product/{productId}/product")
	public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
		try {
			Product product = productService.getProductById(productId);
			ProductDto productDto = productService.convertToDto(product);
			return ResponseEntity.ok(new ApiResponse("Product found !", productDto));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
		try {
			Product theProduct = productService.addProduct(product);
			return ResponseEntity.ok(new ApiResponse("Add product success !", theProduct));
		} catch ( AlreadyExistsExceptoin e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
	@PutMapping("/product/{productId}/product")
	public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequest request, @PathVariable Long productId){
		try {
			Product theProduct = productService.updateProduct(request,productId);
			return ResponseEntity.ok(new ApiResponse("Update product success !", theProduct));
		} catch ( ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), null));
		}		
	}
	
	@DeleteMapping("/product/{productId}/delete")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
		try {
			productService.deleteProductById(productId);
			return ResponseEntity.ok(new ApiResponse("Delete product success !", productId));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/products/by/brand-and-name")
	public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
		try {
			List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
			List<ProductDto> convertedProduct = productService.getConvertedProducts(products);
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ApiResponse("No product found !", null));
			}
			return ResponseEntity.ok(new ApiResponse("Product found !", convertedProduct));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(e.getMessage(), null));
		}
		
	}

	@GetMapping("/products/by/category-and-brand")
	public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@PathVariable String categoryName, @PathVariable String brandName) {
		try {
			List<Product> products = productService.getProductsByBrandAndName(categoryName, brandName);
			List<ProductDto> convertedProduct = productService.getConvertedProducts(products);
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ApiResponse("No product found !", null));
			}
			return ResponseEntity.ok(new ApiResponse("Product found !", convertedProduct));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(e.getMessage(), null));
		}		
	}
	
	@GetMapping("/products/{productName}/product")
	public ResponseEntity<ApiResponse> getProductByName(@PathVariable String productName) {
		try {
			List <Product> products = productService.getProductsByName(productName);
			List<ProductDto> convertedProduct = productService.getConvertedProducts(products);
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ApiResponse("No product found !", null));
			}
			return ResponseEntity.ok(new ApiResponse("Product found !", convertedProduct));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(e.getMessage(), null));
		}		
	}
	
	@GetMapping("/product/by-brand")
	public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand) {
		try {
			List<Product> products = productService.getProductsByBrand(brand);
			List<ProductDto> convertedProduct = productService.getConvertedProducts(products);
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ApiResponse("No product found !", null));
			}
			return ResponseEntity.ok(new ApiResponse("Product found !", convertedProduct));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(e.getMessage(), null));
		}		
	}
	
	@GetMapping("/product/{category}/all/products")
	public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category) {
		try {
			List<Product> products = productService.getProductsByCategory(category);
			List<ProductDto> convertedProduct = productService.getConvertedProducts(products);
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ApiResponse("No product found !", null));
			}
			return ResponseEntity.ok(new ApiResponse("Product found !", convertedProduct));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(e.getMessage(), null));
		}		
	}
	
	@GetMapping("/product/count/by-brand/and-name")
	public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
		try {
			var productCount = productService.countProductsByBrandAndName(brand, name);
			return ResponseEntity.ok(new ApiResponse("Product count !", productCount));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(e.getMessage(), null));
		}		
	}
	
}
