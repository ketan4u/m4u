package com.care.m4u.worker.dto;

import lombok.Data;

@Data
public class WorkerLoginRequest {
    private String username;
    private Long phoneNo;
    private String password;
    private String otp;
    private LoginType loginType;

    public enum LoginType {
        USERNAME_PASSWORD,
        PHONE_PASSWORD,
        PHONE_OTP
    }
} 