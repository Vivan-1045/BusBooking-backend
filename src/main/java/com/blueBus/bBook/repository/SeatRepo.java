package com.blueBus.bBook.repository;

import com.blueBus.bBook.model.Bus;
import com.blueBus.bBook.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepo extends JpaRepository<Seat,Long> {

    List<Seat> findByBusId(Long busId);
}
