package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;

@Builder
public record StatusReservationDto(
        int idStatusReservation,
        String name,
        Boolean isActive
) {
}
