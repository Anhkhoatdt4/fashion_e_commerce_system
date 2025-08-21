package com.pbl6.fashion_web_be.service;

import com.pbl6.fashion_web_be.dto.request.ReviewRequest;
import com.pbl6.fashion_web_be.dto.response.ReviewResponse;
import com.pbl6.fashion_web_be.entity.Product;
import com.pbl6.fashion_web_be.entity.Review;
import com.pbl6.fashion_web_be.entity.User;
import com.pbl6.fashion_web_be.mapper.ReviewMapper;
import com.pbl6.fashion_web_be.repository.ProductRepository;
import com.pbl6.fashion_web_be.repository.ReviewRepository;
import com.pbl6.fashion_web_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    private final ReviewMapper mapper;

    @Transactional
    public ReviewResponse addReview(ReviewRequest request, String userEmail) {
        Product product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = new Review();
        review.setProduct(product);
        review.setUser(user);
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        return mapper.toResponse(reviewRepo.save(review));
    }

    public List<ReviewResponse> getReviewsByProduct(UUID productId) {
        return reviewRepo.findByProductProductId(productId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
