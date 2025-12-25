package com.blueBus.bBook.service;

import com.blueBus.bBook.model.*;
import com.blueBus.bBook.repository.BookedSeatRepo;
import com.blueBus.bBook.repository.SeatLockRepo;
import com.blueBus.bBook.repository.SeatRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatLockService {
    private static final int LOCK_MINUTE = 5;

    private final SeatRepo seatRepo;
    private final SeatLockRepo seatLockRepo;
    private final BookedSeatRepo bookedSeatRepo;

    public void LockSeat(Trip trip, List<Long> seatIds, Stop fromStop, Stop toStop,String bookingId){
        for(Long seatid : seatIds){
            List<SeatLock> confilctSeats = seatLockRepo.findConflictingLocks(
                    trip.getId(),
                    seatid,
                    fromStop.getSeqNumber(),
                    toStop.getSeqNumber());

            if (!confilctSeats.isEmpty()){
                throw new IllegalArgumentException("Seat "+seatid+" is Already locked.");
            }

            Seat seat = seatRepo.findById(seatid)
                    .orElseThrow(() -> new IllegalArgumentException("Seat not found."));

            seatLockRepo.save(
                    SeatLock.builder()
                            .trip(trip)
                            .seat(seat)
                            .fromStop(fromStop)
                            .toStop(toStop)
                            .bookedBy(bookingId)
                            .lockedAt(LocalDateTime.now())
                            .expiresAt(LocalDateTime.now().plusMinutes(LOCK_MINUTE))
                            .build()
            );

        }
    }

    public void releaseLockByBookingId(String bookingId){
        seatLockRepo.deleteByBookedId(bookingId);
    }

    @Transactional
    public void convertSeatToBookedSeat(Booking booking){
        List<SeatLock> locks = seatLockRepo.findByTripAndLockedBy(
                booking.getTrip(),
                booking.getBookingId()
        );

        for (SeatLock seatLock : locks){
            bookedSeatRepo.save(
                    BookedSeat.builder()
                            .booking(booking)
                            .trip(booking.getTrip())
                            .seat(seatLock.getSeat())
                            .fromStop(seatLock.getFromStop())
                            .toStop(seatLock.getToStop())
                            .build()
            );
        }

        seatLockRepo.deleteAll(locks);
    }
}
