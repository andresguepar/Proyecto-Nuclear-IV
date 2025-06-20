package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import lombok.Data;
import pn.proyectonuclear4.entity.ParkingLot;
import pn.proyectonuclear4.entity.VehicleType;

@Data
@Builder
public class StandardFeeDto {
    private int idStandardFee;
    private VehicleType vehicleType;
    private ParkingLot parkingLot;
    private Double priceForHours;
    private Boolean isActivePriceFortTwelveHours;
    private Double priceForTwelveHours;
    private Boolean isActive;
}
