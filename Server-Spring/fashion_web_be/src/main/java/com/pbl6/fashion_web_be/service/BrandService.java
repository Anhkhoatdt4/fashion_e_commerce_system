package com.pbl6.fashion_web_be.service;

import com.pbl6.fashion_web_be.entity.Brand;
import com.pbl6.fashion_web_be.repository.BrandRepository;
import com.pbl6.fashion_web_be.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public Brand getBrandById(UUID id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
    }
}
