package com.blueBus.bBook.repository;

import com.blueBus.bBook.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepo extends JpaRepository<Route,Long> {
    boolean existsByStartCityAndEndCity(String start,String end);
}
