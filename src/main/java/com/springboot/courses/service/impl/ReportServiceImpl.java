package com.springboot.courses.service.impl;

import com.springboot.courses.payload.dashboard.CountSummaryResponse;
import com.springboot.courses.repository.*;
import com.springboot.courses.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired private UserRepository userRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private CoursesRepository coursesRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private BlogRepository blogRepository;
    @Autowired private ContestRepository contestRepository;

    @Override
    public CountSummaryResponse count() {
        CountSummaryResponse response =new CountSummaryResponse();
        response.setTotalUsers((int) userRepository.count());
        response.setTotalCategories((int) categoryRepository.count());
        response.setTotalCourses((int) coursesRepository.count());
        response.setTotalBlogs((int) blogRepository.count());
        response.setTotalQuizzes((int) contestRepository.count());
        response.setTotalOrders((int) orderRepository.count());
        response.setTotalIncomes(orderRepository.sumIncome());
        response.setTotalReviews((int) reviewRepository.count());

        return response;
    }
}
