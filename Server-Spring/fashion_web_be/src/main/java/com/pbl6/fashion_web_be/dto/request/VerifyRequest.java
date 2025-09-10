package com.pbl6.fashion_web_be.dto.request;

import lombok.Data;

@Data
public class VerifyRequest {
    private String email;
    private String code;
}
