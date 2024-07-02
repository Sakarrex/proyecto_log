package org.example.proyecto_log.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="stops")
@Getter
@Setter
@NoArgsConstructor
public class StopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @Column(name = "latitud")
    private String latitud;

    @Column(name = "longitud")
    private String longitud;
}
