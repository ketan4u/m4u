package com.care.m4u.common.otp.service;

import com.care.m4u.common.otp.model.Otp;
import com.care.m4u.common.otp.repository.OtpRepo;
import com.care.m4u.common.sms.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {

    private static final Logger logger = LoggerFactory.getLogger(OtpServiceImpl.class);

    @Autowired
    private OtpRepo otpRepository;

    @Autowired
    private SmsService smsService;

    @Override
    @Transactional
    public String generateOtp(Long phoneNo, Long entityId, String entityType) {
        logger.info("Generating OTP for phone number: {}, entity: {}:{}", phoneNo, entityType, entityId);
        
        // Generate 5-digit OTP
        String otp = String.format("%05d", new Random().nextInt(100000));
        
        // Create and save OTP
        Otp otpEntity = new Otp();
        otpEntity.setOtp(otp);
        otpEntity.setPhoneNo(phoneNo);
        otpEntity.setEntityId(entityId);
        otpEntity.setEntityType(entityType);
        otpRepository.save(otpEntity);
        
        // Send OTP via SMS
        smsService.sendOtp(phoneNo, otp);
        
        logger.info("OTP generated and sent for phone number: {}", phoneNo);
        return otp;
    }

    @Override
    @Transactional
    public boolean validateOtp(Long phoneNo, String otp, Long entityId, String entityType) {
        logger.info("Validating OTP for phone number: {}, entity: {}:{}", phoneNo, entityType, entityId);
        
        Optional<Otp> otpOptional = otpRepository.findByPhoneNoAndOtpAndUsedFalse(phoneNo, otp);
        
        if (otpOptional.isEmpty()) {
            logger.warn("Invalid or expired OTP for phone number: {}", phoneNo);
            return false;
        }
        
        Otp otpEntity = otpOptional.get();
        if (!otpEntity.isValid()) {
            logger.warn("OTP expired or already used for phone number: {}", phoneNo);
            return false;
        }
        
        // Mark OTP as used
        otpEntity.setUsed(true);
        otpRepository.save(otpEntity);
        
        logger.info("OTP validated successfully for phone number: {}", phoneNo);
        return true;
    }

    @Override
    @Transactional
    public void invalidateOtp(Long phoneNo, Long entityId, String entityType) {
        logger.info("Invalidating OTP for phone number: {}, entity: {}:{}", phoneNo, entityType, entityId);
        
        Optional<Otp> otpOptional = otpRepository.findByPhoneNoAndEntityIdAndEntityTypeAndUsedFalse(
            phoneNo, entityId, entityType);
            
        if (otpOptional.isPresent()) {
            Otp otp = otpOptional.get();
            otp.setUsed(true);
            otpRepository.save(otp);
            logger.info("OTP invalidated for phone number: {}", phoneNo);
        }
    }
} 