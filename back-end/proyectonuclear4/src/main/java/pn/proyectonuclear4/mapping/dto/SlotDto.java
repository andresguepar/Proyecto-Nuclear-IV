package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import lombok.Data;
import pn.proyectonuclear4.entity.ParkingLot;
import pn.proyectonuclear4.entity.VehicleType;
@Data
@Builder
public class SlotDto{
    private int idSlot;
    private String name;
    private Boolean isAvailable;
    private Boolean isActive;
    private ParkingLot parkingLot;
    private VehicleType vehicleType;
}


