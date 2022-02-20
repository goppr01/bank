package com.project.bank.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class NoCustomerException extends  RuntimeException {
    private String message;
}
