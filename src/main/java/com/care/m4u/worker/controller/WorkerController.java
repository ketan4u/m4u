package com.care.m4u.worker.controller;

import com.care.m4u.worker.dto.WorkerLoginRequest;
import com.care.m4u.worker.dto.WorkerLoginResponse;
import com.care.m4u.worker.dto.WorkerRegistrationRequest;
import com.care.m4u.worker.dto.WorkerResponse;
import com.care.m4u.worker.service.WorkerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workers")
@Tag(name = "Workers", description = "Worker management endpoints")
public class WorkerController {

    private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);

    @Autowired
    private WorkerService workerService;

    @PostMapping("/register")
    @Operation(summary="API to register a worker", description="saves a new entry to workers table")
    public ResponseEntity<WorkerResponse> registerWorker(@RequestBody WorkerRegistrationRequest request) {
        logger.info("Received registration request for worker with username: {}", request.getUsername());
        
        WorkerResponse response = workerService.registerWorker(request);
        
        logger.info("Successfully processed registration request for worker ID: {}", response.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<WorkerLoginResponse> login(@RequestBody WorkerLoginRequest request) {
        logger.info("Login attempt for type: {}", request.getLoginType());
        
        WorkerLoginResponse response = workerService.login(request);
        
        logger.info("Login successful for worker");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/generate-otp")
    public ResponseEntity<WorkerLoginResponse> generateOtp(@RequestParam Long phoneNo) {
        logger.info("OTP generation request for phone number: {}", phoneNo);
        
        WorkerLoginResponse response = workerService.generateOtp(phoneNo);
        
        logger.info("OTP generated successfully for phone number: {}", phoneNo);
        return ResponseEntity.ok(response);
    }
}
