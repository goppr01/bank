package com.project.bank.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class CustomerAlreadyExists  extends  RuntimeException {
    private String message;
}
