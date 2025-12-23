package com.blueBus.bBook.model;

import com.blueBus.bBook.utility.TripStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id",nullable = false)
    private Bus bus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id",nullable = false)
    private Route route;

    @Column(name = "journey_date",nullable = false)
    private LocalDate journeyDate;

    @Column(name = "depart_time",nullable = false)
    private LocalTime departTime;

    @Column(name = "arrival_time")
    private LocalTime arrivalTime;

    @Enumerated(EnumType.STRING)
    private TripStatus status;
}
