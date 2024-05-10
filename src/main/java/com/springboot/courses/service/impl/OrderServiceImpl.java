package com.springboot.courses.service.impl;

import com.springboot.courses.entity.*;
import com.springboot.courses.exception.BlogApiException;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.order.OrderRequest;
import com.springboot.courses.payload.order.OrderResponse;
import com.springboot.courses.repository.CoursesRepository;
import com.springboot.courses.repository.OrderRepository;
import com.springboot.courses.repository.TrackCourseRepository;
import com.springboot.courses.repository.UserRepository;
import com.springboot.courses.service.OrderService;
import com.springboot.courses.utils.AppConstants;
import com.springboot.courses.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CoursesRepository coursesRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TrackCourseRepository trackCourseRepository;

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        Courses courses = coursesRepository.findById(orderRequest.getCourseId()).orElseThrow(() -> new ResourceNotFoundException("Course", "id", orderRequest.getCourseId()));

        if (orderRepository.existsOrderByCoursesAndUser(courses, user)) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Khách hàng đã từng mua khóa học này!");
        }

        Order order = new Order();
        order.setUser(user);
        order.setCreatedTime(new Date());
        order.setCourses(courses);
        order.setTotalPrice(orderRequest.getTotalPrice());

        Order savedOrder = orderRepository.save(order);

        int totalStudent = courses.getStudentCount();
        courses.setStudentCount(totalStudent + 1);
        coursesRepository.save(courses);

        OrderResponse orderResponse = modelMapper.map(savedOrder, OrderResponse.class);
        orderResponse.setCustomerName(savedOrder.getUser().getFullName());
        orderResponse.setCourseName(savedOrder.getCourses().getTitle());

        for (Chapter chapter : courses.getChapterList()) {
            for (Lesson lesson : chapter.getLessonList()) {
                TrackCourse trackCourse = new TrackCourse();
                trackCourse.setCourses(courses);
                trackCourse.setChapter(chapter);
                trackCourse.setLesson(lesson);
                trackCourse.setUser(user);
                trackCourse.setCompleted(false);
                if (chapter.getOrders() == 1 && lesson.getOrders() == 1) {
                    trackCourse.setUnlock(true);
                    trackCourse.setCurrent(true);
                }

                trackCourseRepository.save(trackCourse);
            }
        }
        return orderResponse;
    }

    public void createOrder(User user, Courses courses, int totalPrice) {

        Order order = new Order();
        order.setUser(user);
        order.setCreatedTime(new Date());
        order.setCourses(courses);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);
        Utils.sendEmailForOrder(AppConstants.SUBJECT_ORDER, AppConstants.CONTENT_ORDER, order);

        int totalStudent = courses.getStudentCount();
        courses.setStudentCount(totalStudent + 1);
        coursesRepository.save(courses);

        for (Chapter chapter : courses.getChapterList()) {
            for (Lesson lesson : chapter.getLessonList()) {
                TrackCourse trackCourse = new TrackCourse();
                trackCourse.setCourses(courses);
                trackCourse.setChapter(chapter);
                trackCourse.setLesson(lesson);
                trackCourse.setUser(user);
                trackCourse.setCompleted(false);
                if (chapter.getOrders() == 1 && lesson.getOrders() == 1) {
                    trackCourse.setUnlock(true);
                    trackCourse.setCurrent(true);
                }

                trackCourseRepository.save(trackCourse);
            }
        }
    }

    @Override
    public List<OrderResponse> getAll() {
        List<Order> listOrders = orderRepository.findAll();
        return convertToListResponse(listOrders);
    }

    @Override
    public String deleteOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        Integer courseId = order.getCourses().getId();
        orderRepository.delete(order);
        Courses courses = coursesRepository.findById(courseId).get();
        int totalStudent = courses.getStudentCount() - 1;
        courses.setStudentCount(totalStudent);
        coursesRepository.save(courses);
        return "Delete order successfully ";
    }

    @Override
    public List<OrderResponse> getAllByUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        List<Order> listOrders = orderRepository.findOrderByUser(user);
        return convertToListResponse(listOrders);
    }

    private List<OrderResponse> convertToListResponse(List<Order> listOrders) {
        List<OrderResponse> listOrderResponse = new ArrayList<>();

        for (Order order : listOrders) {
            OrderResponse response = modelMapper.map(order, OrderResponse.class);
            response.setCourseName(order.getCourses().getTitle());
            response.setCustomerName(order.getUser().getFullName());
            listOrderResponse.add(response);
        }
        return listOrderResponse;
    }

}
