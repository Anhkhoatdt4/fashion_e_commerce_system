package com.pbl6.fashion_web_be.service;

import com.pbl6.fashion_web_be.dto.request.CategoryRequest;
import com.pbl6.fashion_web_be.dto.response.CategoryResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse updateCategory(UUID categoryId, CategoryRequest request);
    void deleteCategory(UUID categoryId);
    CategoryResponse getCategoryById(UUID categoryId);
    List<CategoryResponse> getAllCategories();
}
