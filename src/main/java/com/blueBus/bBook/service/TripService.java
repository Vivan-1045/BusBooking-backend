package com.blueBus.bBook.service;

import com.blueBus.bBook.model.Bus;
import com.blueBus.bBook.model.Route;
import com.blueBus.bBook.model.Trip;
import com.blueBus.bBook.repository.BusRepo;
import com.blueBus.bBook.repository.RouteRepo;
import com.blueBus.bBook.repository.TripRepo;
import com.blueBus.bBook.utility.TripStatus;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TripService {
    private TripRepo tripRepo;
    private RouteRepo routeRepo;
    private BusRepo busRepo;

    //Method to create Trip(Admin)
    public Trip createTrip(Long routeId, Long busId, LocalDate journeyDate, LocalTime departTime){
        if (journeyDate.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Journey Date can't be past.");
        }

        Route route = routeRepo.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException("Route Not found."));

        Bus bus = busRepo.findById(busId)
                .orElseThrow(() -> new IllegalArgumentException("Bus not found."));

        if(tripRepo.existsByRouteIdAndBusIdAndJourneyDate(routeId,busId,journeyDate)){
            throw new IllegalArgumentException("Trip already exist.");
        }

        Trip trip = Trip.builder()
                .bus(bus)
                .route(route)
                .journeyDate(journeyDate)
                .departTime(departTime)
                .status(TripStatus.ACTIVE)
                .build();

        return tripRepo.save(trip);
    }

    @Transactional(readOnly=true)
    public List<Trip> searchTrip(LocalDate journeyDate,Long routeId){
        if (!routeRepo.existsById(routeId)){
            throw new IllegalArgumentException("Route not found.");
        }

        return tripRepo.findByRouteIdAndJourneyDateAndStatus(routeId,journeyDate,TripStatus.ACTIVE);
    }
}
