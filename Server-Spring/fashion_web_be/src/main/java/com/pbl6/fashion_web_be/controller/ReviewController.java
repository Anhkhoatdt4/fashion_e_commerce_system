package com.pbl6.fashion_web_be.controller;

import com.pbl6.fashion_web_be.dto.request.ReviewRequest;
import com.pbl6.fashion_web_be.dto.response.ReviewResponse;
import com.pbl6.fashion_web_be.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ReviewResponse addReview(@RequestBody ReviewRequest request, @RequestHeader("user-email") String userEmail) {
        return reviewService.addReview(request, userEmail);
    }

    @GetMapping("/product/{productId}")
    public List<ReviewResponse> getReviews(@PathVariable UUID productId) {
        return reviewService.getReviewsByProduct(productId);
    }
}
