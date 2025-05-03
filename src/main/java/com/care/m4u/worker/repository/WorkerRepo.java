package com.care.m4u.worker.repository;

import com.care.m4u.worker.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkerRepo extends JpaRepository<Worker, Long> {

    Optional<Worker> findByPhoneNo(Long phoneNo);
    Optional<Worker> findByUsername(String username);

    List<Worker> findByService(String service);

    @Query(value = "SELECT w.*, " +
            "6371 * acos(cos(radians(:lat)) * cos(radians(w.latitude)) * " +
            "cos(radians(w.longitude) - radians(:lng)) + " +
            "sin(radians(:lat)) * sin(radians(w.latitude))) AS distance " +
            "FROM workers w " +
            "WHERE w.service = :service " +
            "ORDER BY distance " +
            "LIMIT 2", nativeQuery = true)
    List<Worker> findNearestWorkersByService(@Param("service") String service,
                                           @Param("lat") Double latitude,
                                           @Param("lng") Double longitude);
}
