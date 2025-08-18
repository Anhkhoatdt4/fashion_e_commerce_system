package com.pbl6.fashion_web_be.service;

import com.pbl6.fashion_web_be.dto.request.*;
import com.pbl6.fashion_web_be.dto.response.*;
import com.pbl6.fashion_web_be.entity.*;
import com.pbl6.fashion_web_be.mapper.ProductMapper;
import com.pbl6.fashion_web_be.repository.*;
import com.pbl6.fashion_web_be.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService{

    ProductRepository productRepository;
    BrandRepository brandRepository;
    CategoryRepository categoryRepository;
    ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(ProductCreateRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
        Brand brand = brandRepository.findById(request.getBrandId()).orElseThrow(() -> new RuntimeException("Brand not found"));
        Product product = productMapper.toProduct(request, category, brand);
        List<ProductImage> productImages = request.getImageUrls().stream()
                .map(url -> {
                    ProductImage img = new ProductImage();
                    img.setImageUrl(url);
                    img.setProduct(product);
                    return img;
                }).collect(Collectors.toList());
        product.setImages(productImages);
        List<ProductVariant> productVariants = request.getVariants().stream()
                .map(variantCreateRequest ->
                    ProductVariant.builder()
                            .color(variantCreateRequest.getColor())
                            .size(variantCreateRequest.getSize())
                            .price(variantCreateRequest.getPrice())
                            .stockQuantity(variantCreateRequest.getStockQuantity())
                            .product(product)
                            .build()
                ).collect(Collectors.toList());
        product.setVariants(productVariants);
        Product savedProduct = productRepository.save(product);
        log.info("Product created {}", savedProduct);
        return productMapper.toProductResponse(productRepository.save(product));
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findByIsActiveTrue().stream()
                .map(product -> productMapper.toProductResponse((Product) product))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toProductResponse(product);
    }

    @Override
    public List<ProductResponse> getProductsByCategory(UUID categoryId) {
        return productRepository.findByCategory_CategoryId(categoryId).stream()
                .map(product -> productMapper.toProductResponse((Product) product))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProductsByBrand(UUID brandId) {
        return productRepository.findByBrand_BrandId(brandId)
                .stream()
                .map(product -> productMapper.toProductResponse((Product) product))
                .collect(Collectors.toList());
    }
}
