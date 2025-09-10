package com.pbl6.fashion_web_be.service;

import com.pbl6.fashion_web_be.dto.request.CategoryRequest;
import com.pbl6.fashion_web_be.dto.response.CategoryResponse;
import com.pbl6.fashion_web_be.entity.Category;
import com.pbl6.fashion_web_be.mapper.CategoryMapper;
import com.pbl6.fashion_web_be.repository.CategoryRepository;
import com.pbl6.fashion_web_be.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category parentCategory = null;
        if (request.getParentCategoryId() != null) {
            parentCategory = categoryRepository.findById(request.getParentCategoryId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
        }
        List<Category> siblingCategories;
        if (parentCategory == null) {
            siblingCategories = categoryRepository.findByParentCategoryIsNullOrderBySortOrderAsc();
        } else {
            siblingCategories = categoryRepository.findByParentCategoryOrderBySortOrderAsc(parentCategory);
        }
        Integer sortOrderToSet;
        if (request.getSortOrder() == null){
            sortOrderToSet = siblingCategories.size() + 1;
        } else {
            boolean isExistingSortOrder = siblingCategories.stream().anyMatch(c -> c.getSortOrder().equals(request.getSortOrder()));
            if (isExistingSortOrder) {
                sortOrderToSet = siblingCategories.size() + 1;
            } else {
                sortOrderToSet = request.getSortOrder();
            }
        }
        request.setSortOrder(sortOrderToSet);
        Category category = categoryMapper.toCategory(request, parentCategory);
        category.setParentCategory(parentCategory);
        categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(UUID categoryId, CategoryRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Optional.ofNullable(request.getCategoryName()).ifPresent(category::setCategoryName);
        Optional.ofNullable(request.getDescription()).ifPresent(category::setDescription);
        Optional.ofNullable(request.getImageUrl()).ifPresent(category::setImageUrl);
        Optional.ofNullable(request.getIsActive()).ifPresent(category::setIsActive);
        Optional.ofNullable(request.getSortOrder()).ifPresent(category::setSortOrder);
        if (request.getParentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(request.getParentCategoryId())
            .orElseThrow(() -> new RuntimeException("Parent category not found"));
            category.setParentCategory(parentCategory);
        }
        else {
            category.setParentCategory(null);
        }
        if (category.getSubcategories() != null) {
            category.getSubcategories().clear();
        }
        categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        if (category.getSubcategories() != null && !category.getSubcategories().isEmpty()) {
            throw new RuntimeException("Cannot delete category with subcategories");
        }
        categoryRepository.delete(category);
    }

    @Override
    public CategoryResponse getCategoryById(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }
}
