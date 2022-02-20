package com.project.bank.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class CustomerAccount {

    private Long customerId;

    private String customerName;

    private BigDecimal Balance;
}
