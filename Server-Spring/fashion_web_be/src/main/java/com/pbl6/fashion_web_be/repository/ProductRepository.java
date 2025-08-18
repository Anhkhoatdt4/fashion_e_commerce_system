package com.pbl6.fashion_web_be.repository;

import com.pbl6.fashion_web_be.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<List<Product>> findByCategory_CategoryId(UUID categoryId);
    Optional<List<Product>> findByBrand_BrandId(UUID brandID);
    Optional<List<Product>> findByCategory_IsActiveTrue();
    Optional<List<Product>> findByIsActiveTrue();
}
