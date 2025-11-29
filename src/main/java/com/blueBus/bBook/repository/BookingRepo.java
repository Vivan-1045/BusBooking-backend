package com.blueBus.bBook.repository;

import com.blueBus.bBook.model.Booking;
import com.blueBus.bBook.model.Trip;
import com.blueBus.bBook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepo extends JpaRepository<Booking , Long> {

    List<Booking> findByUser(User user);
    List<Booking> findByTrip(Trip trip);
}
