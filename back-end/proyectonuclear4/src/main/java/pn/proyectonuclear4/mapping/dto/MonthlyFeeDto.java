package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import pn.proyectonuclear4.entity.VehicleType;
import pn.proyectonuclear4.entity.ParkingLot;

@Builder
public record MonthlyFeeDto(
        int idMonthlyFee,
        VehicleType vehicleType,
        ParkingLot parkingLot,
        Double price,
        Boolean isActive
) {
}
