package com.springboot.courses.repository;

import com.springboot.courses.entity.Courses;
import com.springboot.courses.entity.Review;
import com.springboot.courses.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    boolean existsReviewByUserAndCourses(User user, Courses courses);
}
