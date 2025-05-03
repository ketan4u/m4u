package com.care.m4u.common.otp.service;

public interface OtpService {
    String generateOtp(Long phoneNo, Long entityId, String entityType);
    boolean validateOtp(Long phoneNo, String otp, Long entityId, String entityType);
    void invalidateOtp(Long phoneNo, Long entityId, String entityType);
} 