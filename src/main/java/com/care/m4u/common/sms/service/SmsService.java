package com.care.m4u.common.sms.service;

public interface SmsService {
    void sendOtp(Long phoneNo, String otp);
} 