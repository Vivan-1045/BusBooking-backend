package com.blueBus.bBook.repository;

import com.blueBus.bBook.model.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StopRepo extends JpaRepository<Stop,Long> {
    List<Stop> findByRouteIdOrderBySequenceNumber(Long routeId);
    boolean existsByRouteIdAndSequenceNumber(Long routeId, int sequenceNumber);
}
