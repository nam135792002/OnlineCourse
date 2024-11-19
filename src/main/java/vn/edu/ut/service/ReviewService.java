package vn.edu.ut.service;

import vn.edu.ut.payload.MessageNotice;
import vn.edu.ut.payload.review.ListReviewResponse;
import vn.edu.ut.payload.review.ReviewRequest;
import vn.edu.ut.payload.review.ReviewResponse;

public interface ReviewService {
    ReviewResponse createReview(ReviewRequest reviewRequest);
    ListReviewResponse listAll();
    ReviewResponse updateReview(Integer reviewId, String comment);
    String deleteReview(Integer reviewId);
    ListReviewResponse listAllByCourse(Integer courseId);
    MessageNotice checkCustomerToReviewed(Integer userId, Integer courseId);
}
