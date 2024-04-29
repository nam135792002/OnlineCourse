package com.springboot.courses.controller;

import com.springboot.courses.payload.payment.PaymentRequest;
import com.springboot.courses.payload.payment.TransactionRequest;
import com.springboot.courses.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @PostMapping("/get-info")
    public ResponseEntity<?> getPaymentInfo(@RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(paymentService.getPaymentInfo(paymentRequest));
    }

    @PostMapping("/check-transaction")
    public ResponseEntity<?> checkTransaction(@RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.ok(paymentService.checkTransaction(transactionRequest));
    }
}
