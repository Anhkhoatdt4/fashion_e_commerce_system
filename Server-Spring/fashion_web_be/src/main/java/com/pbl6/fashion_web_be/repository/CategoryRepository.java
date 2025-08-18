package com.pbl6.fashion_web_be.repository;

import com.pbl6.fashion_web_be.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<List<Category>> findByParentCategory_CategoryId(UUID parent);
    List<Category> findAllByParentCategory_CategoryId(UUID parentCategoryId);
}
