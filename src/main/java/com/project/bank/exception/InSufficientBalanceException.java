package com.project.bank.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class InSufficientBalanceException extends RuntimeException {
    private String message;

}
