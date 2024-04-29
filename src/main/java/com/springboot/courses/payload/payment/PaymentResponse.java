package com.springboot.courses.payload.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private String qrCode;
    private String bankNumber;
    private String content;
    private String accountName;
    private String bankBranch;
}
