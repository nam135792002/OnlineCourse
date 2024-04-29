package com.springboot.courses.service;

import com.springboot.courses.payload.payment.PaymentRequest;
import com.springboot.courses.payload.payment.PaymentResponse;
import org.springframework.stereotype.Service;

public interface PaymentService {
    PaymentResponse getPaymentInfo(PaymentRequest paymentRequest);
}
