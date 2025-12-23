package com.blueBus.bBook.repository;

import com.blueBus.bBook.model.BookedSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookedSeatRepo extends JpaRepository<BookedSeat , Long> {

    List<BookedSeat> findByTripIdAndSeatId(Long tripId,Long seatId);

}
