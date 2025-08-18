package com.pbl6.fashion_web_be.controller;

import com.pbl6.fashion_web_be.dto.request.ProductCreateRequest;
import com.pbl6.fashion_web_be.dto.response.ProductResponse;
import com.pbl6.fashion_web_be.dto.response.ApiResponse;
import com.pbl6.fashion_web_be.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ApiResponse<ProductResponse> createProduct(@RequestBody ProductCreateRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(request))
                .message("Product created successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<ProductResponse>> getAllProducts() {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getAllProducts())
                .message("All products retrieved")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable UUID id) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getProductById(id))
                .message("Product retrieved")
                .build();
    }

    @GetMapping("/category/{categoryId}")
    public ApiResponse<List<ProductResponse>> getProductsByCategory(@PathVariable UUID categoryId) {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getProductsByCategory(categoryId))
                .message("Products retrieved by category")
                .build();
    }

    @GetMapping("/brand/{brandId}")
    public ApiResponse<List<ProductResponse>> getProductsByBrand(@PathVariable UUID brandId) {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getProductsByBrand(brandId))
                .message("Products retrieved by brand")
                .build();
    }
}
