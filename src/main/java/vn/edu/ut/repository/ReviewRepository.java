package vn.edu.ut.repository;

import vn.edu.ut.entity.Courses;
import vn.edu.ut.entity.Review;
import vn.edu.ut.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    boolean existsReviewByUserAndCourses(User user, Courses courses);
    List<Review> findReviewByCourses(Courses courses);
}
