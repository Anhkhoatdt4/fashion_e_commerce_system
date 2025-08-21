package com.pbl6.fashion_web_be.mapper;

import com.pbl6.fashion_web_be.dto.request.CategoryRequest;
import com.pbl6.fashion_web_be.dto.response.CategoryResponse;
import com.pbl6.fashion_web_be.entity.Category;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(source = "parentCategory.categoryId", target = "parentCategoryId")
    CategoryResponse toCategoryResponse(Category category);

    @Mapping(target = "categoryId", ignore = true)
    @Mapping(target = "subcategories", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(source = "parentCategoryId", target = "parentCategory.categoryId")
    Category toCategory(CategoryRequest request, @Context Category parentCategory);
}
