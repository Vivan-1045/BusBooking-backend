package com.blueBus.bBook.repository;

import com.blueBus.bBook.model.Route;
import com.blueBus.bBook.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TripRepo extends JpaRepository<Trip, Long> {

    List<Trip> findByRouteAndDepartTime(Route route, LocalDateTime departTime);

    List<Trip> findByDepartTime(LocalDateTime departTime);
}
