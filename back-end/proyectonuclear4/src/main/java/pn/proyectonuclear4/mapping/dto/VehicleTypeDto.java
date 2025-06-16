package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehicleTypeDto {
    private int idVehicleType;
    private String name;
    private Boolean isActive;
}
