package org.example.proyecto_log.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequirementDto {
    private Integer id;
    private Integer idStopDeparture;
    private Integer idStopArrival;
    private String category;
    private String pickupTime;
    private Double loading;


    // Constructor con todos los campos
    public RequirementDto(Integer id, Integer idStopDeparture, Integer idStopArrival, String category, String pickupTime, Double loading) {
        this.id = id;
        this.idStopDeparture = idStopDeparture;
        this.idStopArrival = idStopArrival;
        this.category = category;
        this.pickupTime = pickupTime;
        this.loading = loading;
    }
}