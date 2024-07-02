package org.example.proyecto_log.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="truncks")
@Getter
@Setter
@NoArgsConstructor
public class TrunckEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_stop_departure")
    private Integer idStopDeparture;

    @Column(name = "id_stop_arrival")
    private Integer idStopArrival;

    @Column(name = "category")
    private String category;

    @Column(name = "pickup_time")
    private String pickupTime;

    @Column(name = "id_stop_parking")
    private String idStopParking;

    @Column(name = "capacity")
    private Integer capacity;


}
