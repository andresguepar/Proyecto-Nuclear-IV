package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import pn.proyectonuclear4.entity.ParkingLot;

@Builder
public record AddOnServiceDto(
        int idAddOnService,
        String name,
        String description,
        Double price,
        Boolean isActive,
        ParkingLot parkingLot
) {
}
