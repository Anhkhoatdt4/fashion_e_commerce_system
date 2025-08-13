package com.pbl6.fashion_web_be.exception;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public class AppException extends RuntimeException{

    private final ErrorCode errorCode;

    public AppException (ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}