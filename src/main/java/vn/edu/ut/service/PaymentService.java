package vn.edu.ut.service;

import vn.edu.ut.payload.payment.PaymentRequest;
import vn.edu.ut.payload.payment.PaymentResponse;
import vn.edu.ut.payload.payment.TransactionRequest;

public interface PaymentService {
    PaymentResponse getPaymentInfo(PaymentRequest paymentRequest);
    boolean checkTransaction(TransactionRequest transactionRequest);
}
