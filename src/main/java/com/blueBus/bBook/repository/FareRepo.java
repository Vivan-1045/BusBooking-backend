package com.blueBus.bBook.repository;

import com.blueBus.bBook.model.FareMatrix;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FareRepo extends JpaRepository<FareMatrix,Long> {

    Optional<FareMatrix> findByRouteIdAndFromStopIdAndToStopId(
            Long routeId,
            Long fromStopId,
            Long toStopId
    );

    boolean existsByRouteId(Long routeId);

}
