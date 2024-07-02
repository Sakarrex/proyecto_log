package org.example.proyecto_log.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class StopDto {
    private Integer id;
    private String name;
    private String city;
    private String province;
    private String latitud;
    private String longitud;


    // Constructor con todos los campos
    public StopDto(Integer id, String name, String city, String province, String latitud, String longitud) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.province = province;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}