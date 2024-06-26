package com.springboot.courses.payload.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankResponse {
    private String totalRows;
    private String maxAcentrysmo;
    private BankTransactionInfo[] transactionInfos;
}
