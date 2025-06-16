package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import lombok.Data;
import pn.proyectonuclear4.entity.User;

@Data
@Builder
public class ParkingLotDto {
    private int idParkingLot;
    private User admin;
    private String address;
    private String coordX;
    private String coordY;
    private String name;
    private String nit;
    private String phone;
    private Boolean isActive;
}
