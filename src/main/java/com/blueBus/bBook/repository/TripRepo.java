package com.blueBus.bBook.repository;

import com.blueBus.bBook.model.Trip;
import com.blueBus.bBook.utility.TripStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TripRepo extends JpaRepository<Trip, Long> {

    List<Trip> findByRouteIdAndJourneyDateAndStatus(Long routeId, LocalDate journeyDate, TripStatus status);

    boolean existsByRouteIdAndBusIdAndJourneyDate(Long routeId,Long busId,LocalDate localDate);
}
