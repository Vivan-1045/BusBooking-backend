package com.blueBus.bBook.repository;

import com.blueBus.bBook.model.BookedSeat;
import com.blueBus.bBook.model.Seat;
import com.blueBus.bBook.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookedSeatRepo extends JpaRepository<BookedSeat , Long> {

    List<BookedSeat> findByBooking_Trip(Trip trip);
    boolean existsByBooking_TripAndSeat(Trip trip, Seat seat);

}
