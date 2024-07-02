package org.example.proyecto_log.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class FrameDto {
    private Integer id;
    private Integer idStopDeparture;
    private Integer idStopArrival;
    private Double price;
    private LocalTime departureDatetime;
    private LocalTime arrivalDatetime;


    // Constructor con todos los campos
    public FrameDto(Integer id, Integer idStopDeparture, Integer idStopArrival, Double price, LocalTime departureDatetime, LocalTime arrivalDatetime) {
        this.id = id;
        this.idStopDeparture = idStopDeparture;
        this.idStopArrival = idStopArrival;
        this.price = price;
        this.departureDatetime = departureDatetime;
        this.arrivalDatetime = arrivalDatetime;
    }
}
