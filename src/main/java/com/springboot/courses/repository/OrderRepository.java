package com.springboot.courses.repository;

import com.springboot.courses.entity.Courses;
import com.springboot.courses.entity.Order;
import com.springboot.courses.entity.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    boolean existsOrderByCoursesAndUser(Courses courses, User user);
    List<Order> findAllByCourses(Courses courses);
    List<Order> findOrderByUser(User user);
    @Query("select sum(o.totalPrice) from Order o")
    int sumIncome();
    @Query("select new Order(o.id, o.createdTime, o.totalPrice) from Order o where o.createdTime between ?1 and ?2 order by o.createdTime asc")
    List<Order> findByOrderTimeBetween(Date startTime, Date endTime);
    @Query("select new Order(o.courses.category.name, o.totalPrice) from Order o where o.createdTime between ?1 and ?2")
    List<Order> findWithCategoryAndTimeBetween(Date startDate, Date endDate);
    @Query("select new Order(o.totalPrice, o.courses.title) from Order o where o.createdTime between ?1 and ?2")
    List<Order> findWithCourseAndTimeBetween(Date startDate, Date endDate);
}
