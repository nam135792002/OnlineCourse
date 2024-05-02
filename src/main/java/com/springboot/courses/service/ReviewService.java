package com.springboot.courses.service;

import com.springboot.courses.payload.MessageNotice;
import com.springboot.courses.payload.review.ListReviewResponse;
import com.springboot.courses.payload.review.ReviewRequest;
import com.springboot.courses.payload.review.ReviewResponse;

public interface ReviewService {
    ReviewResponse createReview(ReviewRequest reviewRequest);
    ListReviewResponse listAll();
    ReviewResponse updateReview(Integer reviewId, String comment);
    String deleteReview(Integer reviewId);
    ListReviewResponse listAllByCourse(Integer courseId);
    MessageNotice checkCustomerToReviewed(Integer userId, Integer courseId);
}
