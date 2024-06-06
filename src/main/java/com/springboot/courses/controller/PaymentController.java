package com.springboot.courses.controller;

import com.springboot.courses.payload.payment.PaymentRequest;
import com.springboot.courses.payload.payment.TransactionRequest;
import com.springboot.courses.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@Tag(
        name = "Payment Controller",
        description = "REST APIs related to Payment operations"
)
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Operation(
            summary = "Get Payment Info REST API",
            description = "This REST API is used to get payment information based on the provided payment request"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Payment info retrieved successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/get-info")
    public ResponseEntity<?> getPaymentInfo(@RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(paymentService.getPaymentInfo(paymentRequest));
    }

    @Operation(
            summary = "Check Transaction REST API",
            description = "This REST API is used to check the status of a transaction based on the provided transaction request"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Transaction status checked successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/check-transaction")
    public ResponseEntity<?> checkTransaction(@RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.ok(paymentService.checkTransaction(transactionRequest));
    }
}
