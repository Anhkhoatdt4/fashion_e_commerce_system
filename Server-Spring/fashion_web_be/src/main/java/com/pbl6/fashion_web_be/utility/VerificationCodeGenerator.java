package com.pbl6.fashion_web_be.utility;

public class VerificationCodeGenerator {
    public static String generateCode(int length) {
        StringBuilder code = new StringBuilder();
        String digits = "0123456789";
        for (int i = 0; i < length; i ++){
            code.append(digits.charAt((int)(Math.random() * digits.length())));
        }
        return code.toString();
    }
}
