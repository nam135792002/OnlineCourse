package com.springboot.courses.repository;

import com.springboot.courses.entity.Courses;
import com.springboot.courses.entity.Order;
import com.springboot.courses.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    boolean existsOrderByCoursesAndUser(Courses courses, User user);
}
