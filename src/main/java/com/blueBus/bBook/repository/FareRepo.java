package com.blueBus.bBook.repository;

import com.blueBus.bBook.model.FareMatrix;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FareRepo extends JpaRepository<FareMatrix,Long> {
    FareMatrix findByRouteIdAndFromStopIdAndToStopId(
            Long routeId,
            Long fromStopId,
            Long toStopId
    );

    boolean existsByRouteId(Long routeId);

}
