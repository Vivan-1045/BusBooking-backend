package com.blueBus.bBook.service;

import com.blueBus.bBook.model.BookedSeat;
import com.blueBus.bBook.model.Seat;
import com.blueBus.bBook.model.Stop;
import com.blueBus.bBook.model.Trip;
import com.blueBus.bBook.repository.BookedSeatRepo;
import com.blueBus.bBook.repository.SeatRepo;
import com.blueBus.bBook.repository.StopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookedSeatService {
    private StopRepo stopRepo;
    private SeatRepo seatRepo;
    private BookedSeatRepo bookedSeatRepo;

    public List<Seat> getAvailableSeat(Trip trip,Long fromStopId, Long toStopId){
        Stop fromStop = stopRepo.findById(fromStopId)
                .orElseThrow(() -> new IllegalArgumentException("From stop not exist."));

        Stop toStop = stopRepo.findById(toStopId)
                .orElseThrow(() -> new IllegalArgumentException("To stop not exist."));

        int reqFrom = fromStop.getSeqNumber();
        int reqTo = toStop.getSeqNumber();

        if (reqFrom>=reqTo){
            throw new IllegalArgumentException("Journey can't be started from here.");
        }

        List<Seat> allSeat = seatRepo.findByBusId(trip.getBus().getId());

        return allSeat.stream()
                .filter(seat -> isSeatAvailable(trip.getId(),seat,reqFrom,reqTo))
                .toList();
    }

    private boolean isSeatAvailable(Long  tripId,Seat seat,int reqFrom , int reqTo){
        List<BookedSeat> bookedSeats = bookedSeatRepo.findByTripIdAndSeatId(tripId, seat.getId());

        for (BookedSeat bs : bookedSeats){
            int bookedFrom = bs.getFromStop().getSeqNumber();
            int bookedTo = bs.getToStop().getSeqNumber();

            if(isOverLapping(reqFrom,reqTo,bookedFrom,bookedTo)){
                return false;
            }
        }
        return true;
    }

    private boolean isOverLapping(int reqFrom , int reqTo, int bookedFrom, int bookedTo){
        return reqFrom<bookedTo && reqTo>bookedFrom;
    }



}
