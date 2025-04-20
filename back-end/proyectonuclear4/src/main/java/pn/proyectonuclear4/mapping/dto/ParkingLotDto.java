package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import pn.proyectonuclear4.entity.User;

@Builder
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
) {
}
