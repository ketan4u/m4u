package com.care.m4u.common.sms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {
    private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Override
    public void sendOtp(Long phoneNo, String otp) {
        // TODO: Integrate with actual SMS service provider
        logger.info("Sending OTP {} to phone number {}", otp, phoneNo);
        // For now, just log the OTP. In production, integrate with a service like Twilio, AWS SNS, etc.
    }
} 