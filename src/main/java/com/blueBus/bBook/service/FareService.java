package com.blueBus.bBook.service;

import com.blueBus.bBook.model.FareMatrix;
import com.blueBus.bBook.model.Route;
import com.blueBus.bBook.model.Stop;
import com.blueBus.bBook.repository.FareRepo;
import com.blueBus.bBook.repository.RouteRepo;
import com.blueBus.bBook.repository.StopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FareService {
    private StopRepo stopRepo;
    private RouteRepo routeRepo;
    private FareRepo fareRepo;

    public void generateFareMatrix(Long routeId, double farePerKm){
        Route route = routeRepo.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException("Route doesn't exist."));


        if(fareRepo.existsByRouteId(routeId)){
            throw new IllegalArgumentException("Fare matrix already calculated.");
        }

        List<Stop> stopList = stopRepo.findByRouteIdOrderBySequenceNumber(routeId);

        //Fare calculation logic
        for(int i = 0;i<stopList.size();i++){
            Stop from = stopList.get(i);
            double totalDistanceTill = 0;

            for(int j = i+1;j<stopList.size();j++){
                Stop to = stopList.get(j);
                totalDistanceTill += to.getDistanceFromPrev();

                double fare = totalDistanceTill * farePerKm;

                FareMatrix fm = FareMatrix.builder()
                        .route(route)
                        .fromStop(from)
                        .toStop(to)
                        .fare(Math.round(fare))
                        .build();

                fareRepo.save(fm);
            }
        }

    }
}
