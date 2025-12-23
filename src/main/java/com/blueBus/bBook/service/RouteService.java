package com.blueBus.bBook.service;

import com.blueBus.bBook.model.Route;
import com.blueBus.bBook.model.Stop;
import com.blueBus.bBook.repository.RouteRepo;
import com.blueBus.bBook.repository.StopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RouteService {

    private RouteRepo routeRepo;
    private StopRepo stopRepo;

    //Method to create Route
    public Route createRoute(Route route){
        if (route.getStartCity() == null || route.getStartCity().isBlank()
                || route.getEndCity() == null || route.getEndCity().isBlank()){
            throw new IllegalArgumentException("Start and end city required.");
        }

        if (route.getStartCity().equalsIgnoreCase(route.getEndCity())){
            throw new IllegalArgumentException("Start and End can't be same.");
        }

        if (routeRepo.existsByStartCityAndEndCity(route.getStartCity(), route.getEndCity())){
            throw new IllegalArgumentException("Route already exist.");
        }

        return routeRepo.save(route);
    }

    //Method to Add stop in existing route
    public Stop addStop(Long routeId,String stopName, int order){
        if (order<=0){
            throw new IllegalArgumentException("Stop order must be positive and consecutive.");
        }

        Route route = routeRepo.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException("Route not found"));

        if(stopName == null || stopName.isBlank()){
            throw new IllegalArgumentException("Stop name can't be null.");
        }

        if (stopRepo.existsByRouteIdAndSequenceNumber(routeId,order)){
            throw new IllegalArgumentException("Stop order already exists in this route");
        }

        Stop stop = Stop.builder()
                .stopName(stopName)
                .seqNumber(order)
                .route(route)
                .build();

        return stopRepo.save(stop);
    }


    //Method to get All stops of a route
    @Transactional(readOnly = true)
    public List<Stop> getStopByRoute(Long routeId){
        if (!routeRepo.existsById(routeId)){
            throw new IllegalArgumentException("Route not found");
        }

        return stopRepo.findByRouteIdOrderBySequenceNumber(routeId);
    }

}
