package pn.proyectonuclear4.mapping.dto;

import pn.proyectonuclear4.entity.User;

public record ParkingLotDto(
    int idParkingLot,
    User admin,
    String address,
    String coordX,
    String coordY,
    String name,
    String nit,
    String phone,
    Boolean isActive
) {}
