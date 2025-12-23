package com.blueBus.bBook.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stopName;
    private int seqNumber;

    private double distanceFromPrev;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id",nullable = false)
    private Route route;
}
