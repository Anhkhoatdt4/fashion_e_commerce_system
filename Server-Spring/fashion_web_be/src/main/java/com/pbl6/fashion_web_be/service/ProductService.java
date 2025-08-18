package com.pbl6.fashion_web_be.service;

import com.pbl6.fashion_web_be.dto.request.ProductCreateRequest;
import com.pbl6.fashion_web_be.dto.response.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponse createProduct(ProductCreateRequest request);
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(UUID productId);
    List<ProductResponse> getProductsByCategory(UUID categoryId);
    List<ProductResponse> getProductsByBrand(UUID brandId);
}
