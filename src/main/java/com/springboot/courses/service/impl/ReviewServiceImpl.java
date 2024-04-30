package com.springboot.courses.service.impl;

import com.springboot.courses.entity.Courses;
import com.springboot.courses.entity.Review;
import com.springboot.courses.entity.User;
import com.springboot.courses.exception.BlogApiException;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.review.ListReviewResponse;
import com.springboot.courses.payload.review.ReviewRequest;
import com.springboot.courses.payload.review.ReviewResponse;
import com.springboot.courses.repository.CoursesRepository;
import com.springboot.courses.repository.ReviewRepository;
import com.springboot.courses.repository.UserRepository;
import com.springboot.courses.service.ReviewService;
import com.springboot.courses.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired private ReviewRepository reviewRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CoursesRepository coursesRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public ReviewResponse createReview(ReviewRequest reviewRequest) {
        User user = userRepository.findById(reviewRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewRequest.getUserId()));

        Courses courses = coursesRepository.findById(reviewRequest.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Courses", "id", reviewRequest.getCourseId()));

        if (reviewRepository.existsReviewByUserAndCourses(user, courses)){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Tài khoản " + user.getUsername() + " đã đánh giá khóa học này trước đó!");
        }

        Review review = new Review();
        review.setComment(reviewRequest.getComment());
        review.setRating(reviewRequest.getRating());
        review.setUser(user);
        review.setCourses(courses);
        review.setReviewTime(new Date());

        Review savedReview = reviewRepository.save(review);

        return convertToResponse(savedReview);
    }

    @Override
    public ListReviewResponse listAll() {
        List<Review> listReviews = reviewRepository.findAll();
        return convertToListReview(listReviews);
    }

    @Override
    public ReviewResponse updateReview(Integer reviewId, String comment){
        Review reviewInDB = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));

        reviewInDB.setComment(comment);
        Review savedReview = reviewRepository.save(reviewInDB);

        return convertToResponse(savedReview);
    }

    @Override
    public String deleteReview(Integer reviewId) {
        Review reviewInDB = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));

        reviewRepository.delete(reviewInDB);
        return "Xóa đánh giá thành công!";
    }

    @Override
    public ListReviewResponse listAllByCourse(Integer courseId) {
        Courses courses = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));
        List<Review> listReview = reviewRepository.findReviewByCourses(courses);
        return convertToListReview(listReview);
    }

    private ListReviewResponse convertToListReview(List<Review> listReviews) {
        ListReviewResponse listReviewResponse = new ListReviewResponse();
        listReviewResponse.setListResponses(
                listReviews.stream().map(this::convertToResponse).toList()
        );
        int totalReview = listReviews.size();
        listReviewResponse.setTotalReview(totalReview);
        int totalRating = listReviews.stream().mapToInt(Review::getRating).sum();
        double averageReview = (double) totalRating / totalReview;
        averageReview = Math.round(averageReview * 10.0) / 10.0;
        listReviewResponse.setAverageReview(averageReview);
        return listReviewResponse;
    }

    private ReviewResponse convertToResponse(Review savedReview) {
        ReviewResponse response = modelMapper.map(savedReview, ReviewResponse.class);
        Instant now = Instant.now();
        response.setTimeFormatted(Utils.formatDuration(Duration.between(savedReview.getReviewTime().toInstant(), now)));
        response.setUserId(savedReview.getUser().getId());
        response.setUsername(savedReview.getUser().getUsername());
        response.setPhotoUser(savedReview.getUser().getPhoto());
        response.setCourseId(savedReview.getCourses().getId());

        return response;
    }
}
