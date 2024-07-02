package org.example.proyecto_log.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TrunckDto {
        private Integer idStopDeparture;
        private Integer idStopArrival;
        private String category;
        private String pickupTime;
        private String idStopParking;
        private Integer capacity;
        private Integer id;



        // Constructor con todos los campos
        public TrunckDto(Integer idStopDeparture, Integer idStopArrival, String category, String pickupTime, String idStopParking, Integer capacity, Integer id) {
            this.idStopDeparture = idStopDeparture;
            this.idStopArrival = idStopArrival;
            this.category = category;
            this.pickupTime = pickupTime;
            this.idStopParking = idStopParking;
            this.capacity = capacity;
            this.id = id;
        }


    }
