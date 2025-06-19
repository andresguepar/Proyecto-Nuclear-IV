package pn.proyectonuclear4.mapping.dto;

public record StatusReservationDto(
        int idStatusReservation,
        String name,
        Boolean isActive
) {}
