package vn.edu.ut.service.impl;

import vn.edu.ut.entity.Courses;
import vn.edu.ut.entity.Review;
import vn.edu.ut.entity.User;
import vn.edu.ut.exception.AppException;
import vn.edu.ut.exception.ResourceNotFoundException;
import vn.edu.ut.payload.MessageNotice;
import vn.edu.ut.payload.review.ListReviewResponse;
import vn.edu.ut.payload.review.ReviewRequest;
import vn.edu.ut.payload.review.ReviewResponse;
import vn.edu.ut.repository.CoursesRepository;
import vn.edu.ut.repository.OrderRepository;
import vn.edu.ut.repository.ReviewRepository;
import vn.edu.ut.repository.UserRepository;
import vn.edu.ut.service.ReviewService;
import vn.edu.ut.utils.Utils;
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
    @Autowired private OrderRepository orderRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public ReviewResponse createReview(ReviewRequest reviewRequest) {
        User user = userRepository.findById(reviewRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewRequest.getUserId()));

        Courses courses = coursesRepository.findById(reviewRequest.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Courses", "id", reviewRequest.getCourseId()));

        if(!orderRepository.existsOrderByCoursesAndUser(courses, user)){
            throw new AppException(HttpStatus.BAD_REQUEST, "Tài khoản " + user.getUsername() + " chưa từng mua khóa học này trước đó!");
        }

        if (reviewRepository.existsReviewByUserAndCourses(user, courses)){
            throw new AppException(HttpStatus.BAD_REQUEST, "Tài khoản " + user.getUsername() + " đã đánh giá khóa học này trước đó!");
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

    @Override
    public MessageNotice checkCustomerToReviewed(Integer userId, Integer courseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", userId));

        Courses courses = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Courses", "id", courseId));

        if(orderRepository.existsOrderByCoursesAndUser(courses, user)){
            if(reviewRepository.existsReviewByUserAndCourses(user, courses)){
                return new MessageNotice(false, "Bạn đã đánh giá khóa học này trước đó!");
            }
            return new MessageNotice(true, "Bạn vui lòng đánh giá khóa học này!");
        }

        return new MessageNotice(false, "Bạn chưa từng mua khóa học này nên không thể đánh giá!");
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
        response.setTitleCourse(savedReview.getCourses().getTitle());

        return response;
    }
}
