package com.care.m4u.worker.service;

import com.care.m4u.common.otp.service.OtpService;
import com.care.m4u.worker.dto.WorkerLoginRequest;
import com.care.m4u.worker.dto.WorkerLoginResponse;
import com.care.m4u.worker.dto.WorkerRegistrationRequest;
import com.care.m4u.worker.dto.WorkerResponse;
import com.care.m4u.worker.model.Worker;
import com.care.m4u.worker.repository.WorkerRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Date;

@Service
public class WorkerServiceImpl implements WorkerService {

    private static final Logger logger = LoggerFactory.getLogger(WorkerServiceImpl.class);
    private static final Key key = Keys.secretKeyFor( SignatureAlgorithm.HS256);
    private static final String ENTITY_TYPE = "WORKER";

    @Autowired
    private WorkerRepo workerRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public WorkerResponse registerWorker(WorkerRegistrationRequest request) {
        logger.info("Starting worker registration for username: {}", request.getUsername());
        
        Worker worker = new Worker();
        worker.setName(request.getName());
        worker.setUsername(request.getUsername());
        worker.setEmail(request.getEmail());
        worker.setPassword(passwordEncoder.encode(request.getPassword()));
        worker.setPhoneNo(request.getPhoneNo());
        worker.setService(request.getService());
        worker.setExperience(request.getExperience());
        worker.setPreviousWorks(request.getPreviousWorks());

        logger.debug("Worker entity created with email: {}", worker.getEmail());
        
        Worker savedWorker = workerRepository.save(worker);
        logger.info("Worker successfully registered with ID: {}", savedWorker.getId());
        
        WorkerResponse response = mapToResponse(savedWorker);
        logger.debug("Response DTO created for worker ID: {}", response.getId());
        
        return response;
    }

    @Override
    @Transactional
    public WorkerLoginResponse login(WorkerLoginRequest request) {
        logger.info("Login attempt for type: {}", request.getLoginType());
        
        Worker worker = null;
        boolean isValid = false;

        switch (request.getLoginType()) {
            case USERNAME_PASSWORD:
                worker = workerRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new RuntimeException("Worker not found"));
                isValid = passwordEncoder.matches(request.getPassword(), worker.getPassword());
                break;
                
            case PHONE_PASSWORD:
                worker = workerRepository.findByPhoneNo(request.getPhoneNo())
                    .orElseThrow(() -> new RuntimeException("Worker not found"));
                isValid = passwordEncoder.matches(request.getPassword(), worker.getPassword());
                break;
                
            case PHONE_OTP:
                worker = workerRepository.findByPhoneNo(request.getPhoneNo())
                    .orElseThrow(() -> new RuntimeException("Worker not found"));
                isValid = otpService.validateOtp(request.getPhoneNo(), request.getOtp(), worker.getId(), ENTITY_TYPE);
                break;
        }

        if (!isValid) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = generateToken(worker);
        WorkerResponse workerResponse = mapToResponse(worker);
        
        WorkerLoginResponse response = new WorkerLoginResponse();
        response.setToken(token);
        response.setWorker(workerResponse);
        response.setMessage("Login successful");
        
        logger.info("Login successful for worker ID: {}", worker.getId());
        return response;
    }

    @Override
    @Transactional
    public WorkerLoginResponse generateOtp(Long phoneNo) {
        logger.info("Generating OTP for phone number: {}", phoneNo);
        
        // Check if worker exists
        Worker worker = workerRepository.findByPhoneNo(phoneNo)
            .orElseThrow(() -> new RuntimeException("Worker not found"));

        // Generate and send OTP using common service
        otpService.generateOtp(phoneNo, worker.getId(), ENTITY_TYPE);
        
        logger.info("OTP generated and sent for phone number: {}", phoneNo);
        
        WorkerLoginResponse response = new WorkerLoginResponse();
        response.setMessage("OTP sent successfully");
        return response;
    }

    private String generateToken(Worker worker) {
        return Jwts.builder()
            .setSubject(worker.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 hours
            .signWith(key)
            .compact();
    }

    private WorkerResponse mapToResponse(Worker worker) {
        WorkerResponse response = new WorkerResponse();
        response.setId(worker.getId());
        response.setName(worker.getName());
        response.setUsername(worker.getUsername());
        response.setEmail(worker.getEmail());
        response.setPhoneNo(worker.getPhoneNo());
        response.setService(worker.getService());
        response.setExperience(worker.getExperience());
        response.setPreviousWorks(worker.getPreviousWorks());
        return response;
    }
}
