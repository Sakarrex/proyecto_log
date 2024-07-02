package org.example.proyecto_log.model.mapper;

import org.example.proyecto_log.model.dto.StopDto;
import org.example.proyecto_log.persistence.entity.StopEntity;
import org.springframework.stereotype.Component;

@Component
public class StopMapper {
    public StopDto toDto(StopEntity stopEntity) {
        StopDto stopDto = new StopDto();
        stopDto.setId(stopEntity.getId());
        stopDto.setName(stopEntity.getName());
        stopDto.setCity(stopEntity.getCity());
        stopDto.setLatitud(stopEntity.getLatitud());
        stopDto.setLongitud(stopEntity.getLongitud());
        stopDto.setProvince(stopEntity.getProvince());
        return stopDto;

    }
    public StopEntity toEntity(StopDto stopDto) {
        StopEntity stopEntity = new StopEntity();
        stopEntity.setId(stopDto.getId());
        stopEntity.setName(stopDto.getName());
        stopEntity.setCity(stopDto.getCity());
        stopEntity.setLatitud(stopDto.getLatitud());
        stopEntity.setLongitud(stopDto.getLongitud());
        stopEntity.setProvince(stopDto.getProvince());
        return stopEntity;
    }
}
