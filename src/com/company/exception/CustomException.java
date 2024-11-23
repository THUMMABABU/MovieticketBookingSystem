package com.company.exception;

import javax.management.RuntimeMBeanException;

public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
}
