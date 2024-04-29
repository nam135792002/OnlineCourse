package com.springboot.courses.service.impl;

import com.springboot.courses.entity.Courses;
import com.springboot.courses.entity.User;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.payment.PaymentRequest;
import com.springboot.courses.payload.payment.PaymentResponse;
import com.springboot.courses.repository.CoursesRepository;
import com.springboot.courses.repository.UserRepository;
import com.springboot.courses.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    final String BANK_NUMBER = "90813535314";
    final String ACCOUNT_NAME = "PHẠM NGỌC VIỄN ĐÔNG";
    final String BANK_BRANCH = "TPBANK TP.HCM";

    @Autowired
    private CoursesRepository coursesRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public PaymentResponse getPaymentInfo(PaymentRequest paymentRequest) {
        Courses course = coursesRepository.findById(paymentRequest.getCourseId()).orElseThrow(() -> new ResourceNotFoundException("Course", "id", paymentRequest.getCourseId()));
        User user = userRepository.findByEmail(paymentRequest.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User", "email", paymentRequest.getEmail()));

        String content = String.format("TC%02d%02d", user.getId(), course.getId());
        int amount = (int) (course.getPrice() - (course.getPrice() * course.getDiscount()));

        String qrCode = String.format("https://img.vietqr.io/image/970423-%s-qr_only.png?amount=%d&addInfo=%s&accountName=%s", BANK_NUMBER, amount, content, ACCOUNT_NAME);

        return PaymentResponse.builder()
                .qrCode(qrCode)
                .bankNumber(BANK_NUMBER)
                .content(content)
                .accountName(ACCOUNT_NAME)
                .bankBranch(BANK_BRANCH)
                .build();
    }
}
