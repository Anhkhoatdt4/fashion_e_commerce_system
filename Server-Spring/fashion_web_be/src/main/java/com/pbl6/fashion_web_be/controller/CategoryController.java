package com.pbl6.fashion_web_be.controller;

import com.pbl6.fashion_web_be.dto.request.CategoryRequest;
import com.pbl6.fashion_web_be.dto.response.ApiResponse;
import com.pbl6.fashion_web_be.dto.response.CategoryResponse;
import com.pbl6.fashion_web_be.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryService categoryService;

    @PostMapping
    public ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.createCategory(categoryRequest))
                .message("Category created successfully")
                .build();
    }

    @PutMapping("/{categoryId}")
    public ApiResponse<CategoryResponse> updateCategory(@PathVariable UUID categoryId,
                                                        @RequestBody CategoryRequest categoryRequest) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.updateCategory(categoryId, categoryRequest))
                .message("Category updated successfully")
                .build();
    }

    @DeleteMapping("/{categoryId}")
    public ApiResponse<String> deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return ApiResponse.<String>builder()
                .result("Category deleted successfully")
                .build();
    }

    @GetMapping("/{categoryId}")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable UUID categoryId) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.getCategoryById(categoryId))
                .message("Category fetched successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getAllCategories())
                .message("All categories fetched successfully")
                .build();
    }
}
