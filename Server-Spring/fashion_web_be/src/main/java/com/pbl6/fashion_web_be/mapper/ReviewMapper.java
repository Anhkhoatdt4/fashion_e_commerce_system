package com.pbl6.fashion_web_be.mapper;

import com.pbl6.fashion_web_be.dto.response.ReviewResponse;
import com.pbl6.fashion_web_be.entity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    default ReviewResponse toResponse(Review review) {
        if (review == null) return null;
        ReviewResponse res = new ReviewResponse();
        res.setReviewId(review.getReviewId());
        res.setProductId(review.getProduct().getProductId());
        res.setUserId(review.getUser().getProfileId());
        res.setRating(review.getRating());
        res.setComment(review.getComment());
        return res;
    }
}
