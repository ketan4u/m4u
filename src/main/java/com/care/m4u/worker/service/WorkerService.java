package com.care.m4u.worker.service;

import com.care.m4u.worker.dto.WorkerLoginRequest;
import com.care.m4u.worker.dto.WorkerLoginResponse;
import com.care.m4u.worker.dto.WorkerRegistrationRequest;
import com.care.m4u.worker.dto.WorkerResponse;

public interface WorkerService {
    WorkerResponse registerWorker(WorkerRegistrationRequest request);
    WorkerLoginResponse login(WorkerLoginRequest request);
    WorkerLoginResponse generateOtp(Long phoneNo);
}
