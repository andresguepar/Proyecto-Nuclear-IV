package pn.proyectonuclear4.mapping.mappers;

import pn.proyectonuclear4.entity.StatusReservation;
import pn.proyectonuclear4.mapping.dto.StatusReservationDto;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class StatusReservationMapper {
    public static StatusReservationDto mapFrom(StatusReservation source) {
        return StatusReservationDto.builder()
                .idStatusReservation(source.getIdStatusReservation())
                .name(source.getName())
                .isActive(source.getIsActive())
                .build();
    }

    public static StatusReservation mapFrom(StatusReservationDto source) {
        return StatusReservation.builder()
                .idStatusReservation(source.getIdStatusReservation())
                .name(source.getName())
                .isActive(source.getIsActive())
                .build();
    }

    public static List<StatusReservationDto> mapFrom(List<StatusReservation> source) {
        return source.stream().map(StatusReservationMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<StatusReservation> mapToEntities(List<StatusReservationDto> source) {
        return source.stream().map(StatusReservationMapper::mapFrom).collect(Collectors.toList());
    }
}
