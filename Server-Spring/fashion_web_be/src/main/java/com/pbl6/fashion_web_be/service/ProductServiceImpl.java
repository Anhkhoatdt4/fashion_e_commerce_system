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
        Brand brand;
        if (request.getBrandName() != null) {
            if (brandRepository.findByBrandName(request.getBrandName()).isPresent()) {
                brand = brandRepository.findByBrandName(request.getBrandName())
                        .orElseThrow(() -> new RuntimeException("Brand not found"));
            } else {
                brand = new Brand();
                brand.setBrandName(request.getBrandName());
                brand.setDescription("Thương hiệu thể thao nổi tiếng " + request.getBrandName());
                brandRepository.save(brand);
            }
        } else {
            throw new RuntimeException("Brand information missing");
        }
        Product product = productMapper.toProduct(request, category, brand);
        List<ProductImage> productImages = mapProductImages(request.getImageUrls(), product);
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

    private List<ProductImage> mapProductImages(List<ProductImageRequest> imagesRequest, Product product) {
        List<ProductImage> productImages = new ArrayList<>();
        boolean hasMain = imagesRequest.stream().anyMatch(img -> Boolean.TRUE.equals(img.getIsMain()));

        for (int i = 0; i < imagesRequest.size(); i++) {
            ProductImageRequest imgReq = imagesRequest.get(i);
            boolean isMain = hasMain ? Boolean.TRUE.equals(imgReq.getIsMain()) : (i == 0);

            productImages.add(ProductImage.builder()
                    .imageUrl(imgReq.getImageUrl())
                    .isMain(isMain)
                    .product(product)
                    .build());
        }

        return productImages;
    }
}
