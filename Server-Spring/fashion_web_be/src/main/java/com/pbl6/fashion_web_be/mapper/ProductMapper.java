package com.pbl6.fashion_web_be.mapper;

import com.pbl6.fashion_web_be.dto.request.*;
import com.pbl6.fashion_web_be.dto.response.*;
import com.pbl6.fashion_web_be.entity.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "brand.brandName", target = "brandName")
    @Mapping(source = "category.categoryName", target = "categoryName")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "isActive", target = "isActive")
    @Mapping(source = "images", target = "images")
    @Mapping(source = "variants", target = "variants")
    ProductResponse toProductResponse(Product product);

    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "variants", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "description", source = "request.description")
    Product toProduct(ProductCreateRequest request, Category category, Brand brand);

    default ProductVariantResponse toVariantResponse(ProductVariant productVariant){
        if (productVariant == null) {
            return null;
        }
        return ProductVariantResponse.builder()
                .variantId(productVariant.getVariantId())
                .price(productVariant.getPrice())
                .stockQuantity(productVariant.getStockQuantity())
                .size(productVariant.getSize())
                .color(productVariant.getColor())
                .build();
    }

    default List<String> mapProductImageToUrlString(List<ProductImage> images){
        if (images == null || images.isEmpty()) {
            return null;
        }
        return images.stream()
                .map(ProductImage::getImageUrl)
                .toList();
    }

    default List<ProductVariantResponse> mapVariantsToResponses(List<ProductVariant> variants) {
        if (variants == null || variants.isEmpty()) {
            return null;
        }
        return variants.stream()
                .map(this::toVariantResponse)
                .toList();
    }
}
