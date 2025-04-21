package pn.proyectonuclear4.mapping;

import org.mapstruct.Mapper;
import pn.proyectonuclear4.entity.StandardReservation;
import pn.proyectonuclear4.mapping.dto.StandardReservationDto;

@Mapper(componentModel = "spring")
public interface StandardReservationMapper {
    StandardReservationDto toDto(StandardReservation standardReservation);
    StandardReservation toEntity(StandardReservationDto standardReservationDto);
} 