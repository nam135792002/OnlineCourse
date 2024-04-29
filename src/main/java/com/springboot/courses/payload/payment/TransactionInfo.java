package com.springboot.courses.payload.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionInfo {
    private String id;
    private String arrangementId;
    private String reference;
    private String description;
    private String category;
    private String bookingDate;
    private String valueDate;
    private String amount;
    private String currency;
    private String creditDebitIndicator;
    private String runningBalance;
}
