package org.example.proyecto_log.model.mapper;

import org.example.proyecto_log.model.dto.TrunckDto;
import org.example.proyecto_log.persistence.entity.TrunckEntity;
import org.springframework.stereotype.Component;

@Component
public class TrunckMapper {
    public TrunckEntity toEntity(TrunckDto trunckDto) {
        TrunckEntity trunckEntity = new TrunckEntity();
        trunckEntity.setId(trunckDto.getId());
        trunckEntity.setCapacity(trunckDto.getCapacity());
        trunckEntity.setCategory(trunckDto.getCategory());
        trunckEntity.setPickupTime(trunckDto.getPickupTime());
        trunckEntity.setIdStopArrival(trunckDto.getIdStopArrival());
        trunckEntity.setIdStopDeparture(trunckDto.getIdStopDeparture());
        trunckEntity.setIdStopParking(trunckDto.getIdStopParking());
        return trunckEntity;
    }
    public TrunckDto toDto(TrunckEntity trunckEntity) {
        TrunckDto trunckDto = new TrunckDto();
        trunckDto.setId(trunckEntity.getId());
        trunckDto.setCapacity(trunckEntity.getCapacity());
        trunckDto.setCategory(trunckEntity.getCategory());
        trunckDto.setPickupTime(trunckEntity.getPickupTime());
        trunckDto.setIdStopArrival(trunckEntity.getIdStopArrival());
        trunckDto.setIdStopDeparture(trunckEntity.getIdStopDeparture());
        trunckDto.setIdStopParking(trunckEntity.getIdStopParking());
        return trunckDto;
    }
}
