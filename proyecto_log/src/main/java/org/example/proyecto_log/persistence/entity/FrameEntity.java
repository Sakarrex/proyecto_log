package org.example.proyecto_log.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name="frames")
@Getter
@Setter
@NoArgsConstructor
public class FrameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_stop_departure")
    private Integer idStopDeparture;

    @Column(name = "id_stop_arrival")
    private Integer idStopArrival;

    @Column(name = "price")
    private Double price;

    @Column(name = "departure_datetime")
    private LocalTime departureDatetime;

    @Column(name = "arrival_datetime")
    private LocalTime arrivalDatetime;
}
