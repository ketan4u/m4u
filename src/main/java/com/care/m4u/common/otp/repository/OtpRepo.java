package com.care.m4u.common.otp.repository;

import com.care.m4u.common.otp.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepo extends JpaRepository<Otp, Long> {
    Optional<Otp> findByPhoneNoAndOtpAndUsedFalse(Long phoneNo, String otp);
    Optional<Otp> findTopByPhoneNoAndUsedFalseOrderByCreatedAtDesc(Long phoneNo);
    Optional<Otp> findByPhoneNoAndEntityIdAndEntityTypeAndUsedFalse(Long phoneNo, Long entityId, String entityType);
} 