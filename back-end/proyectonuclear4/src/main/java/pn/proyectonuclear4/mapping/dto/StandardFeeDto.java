package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import pn.proyectonuclear4.entity.VehicleType;
import pn.proyectonuclear4.entity.ParkingLot;

@Builder
public record StandardFeeDto(
        int idStandardFee,
        VehicleType vehicleType,
        ParkingLot parkingLot,
        Double priceForHours,
        Boolean isActivePriceFortTwelveHours,
        Double priceForTwelveHours,
        Boolean isActive
) {
}