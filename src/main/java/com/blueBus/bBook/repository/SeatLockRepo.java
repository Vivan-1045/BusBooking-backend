package com.blueBus.bBook.repository;

import com.blueBus.bBook.model.SeatLock;
import com.blueBus.bBook.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface SeatLockRepo extends JpaRepository<SeatLock,Long> {

    @Query("""
            SELECT s1 FROM SeatLock s1
            WHERE s1.trip.id = :tripId
            AND s1.seat.id = :seatId
            AND s1.expiresAt > CURRENT_TIMESTAMP
            AND s1.fromStop.seqNumber < :toSeq
            AND s1.toStop.seqNumber > :fromSeq
            """)

    List<SeatLock> findConflictingLocks(
            Long tripId,
            Long seatId,
            int fromSeq,
            int toSeq
    );

    List<SeatLock> findByTripAndLockedBy(Trip trip,String bookingId);

    void deleteByExpiresAtBefore(LocalDateTime time);
    void deleteByBookedId(String bookingId);
}
