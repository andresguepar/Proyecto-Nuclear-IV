package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import lombok.Data;
import pn.proyectonuclear4.entity.ParkingLot;
import pn.proyectonuclear4.entity.VehicleType;
@Data
@Builder
public class MonthlyFeeDto{
        private int idMonthlyFee;
        private VehicleType vehicleType;
        private ParkingLot parkingLot;
        private Double price;
        private Boolean isActive;
}
