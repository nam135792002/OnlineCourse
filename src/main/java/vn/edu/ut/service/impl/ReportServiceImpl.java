package vn.edu.ut.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.ut.payload.dashboard.CountSummaryResponse;
import vn.edu.ut.repository.*;
import vn.edu.ut.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CoursesRepository coursesRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private ContestRepository contestRepository;

    @Override
    public CountSummaryResponse count() {
        CountSummaryResponse response = new CountSummaryResponse();
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
