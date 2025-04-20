package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;

@Builder
public record VehicleTypeDto(
        int idVehicleType,
        String name,
        Boolean isActive
) {
}
