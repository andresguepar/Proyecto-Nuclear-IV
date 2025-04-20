package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import pn.proyectonuclear4.entity.ParkingLot;
import pn.proyectonuclear4.entity.VehicleType;

@Builder
public record SlotDto(
        int idSlot,
        String name,
        Boolean isAvailable,
        Boolean isActive,
        ParkingLot parkingLot,
        VehicleType vehicleType
) {
}
