package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import lombok.Data;
import pn.proyectonuclear4.entity.ParkingLot;
@Data
@Builder
public class AddOnServiceDto{
    private int idAddOnService;
    private String name;
    private String description;
    private Double price;
    private Boolean isActive;
    private ParkingLot parkingLot;
}
