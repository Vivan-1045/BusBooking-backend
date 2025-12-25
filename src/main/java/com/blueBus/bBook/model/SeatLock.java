package com.blueBus.bBook.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"trip_id","seat_id","from_stop_id","to_stop_id"}
        )
)
public class SeatLock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Trip trip;

    @ManyToOne
    private Seat seat;

    @ManyToOne
    private Stop fromStop;

    @ManyToOne
    private Stop toStop;

    private String bookedBy;
    private LocalDateTime lockedAt;
    private LocalDateTime expiresAt;

}
