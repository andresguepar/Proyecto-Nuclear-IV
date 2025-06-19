package pn.proyectonuclear4.mapping.mappers;

import pn.proyectonuclear4.entity.ParkingLot;
import pn.proyectonuclear4.mapping.dto.ParkingLotDto;

import java.util.List;
import java.util.stream.Collectors;

public class ParkingLotMapper {
    public static ParkingLotDto mapFrom(ParkingLot source) {
        return new ParkingLotDto(
                source.getIdParkingLot(),
                source.getAdmin(),
                source.getAddress(),
                source.getCoordX(),
                source.getCoordY(),
                source.getName(),
                source.getNit(),
                source.getPhone(),
                source.getIsActive()
        );
    }

    public static ParkingLot mapFrom(ParkingLotDto source) {
        return ParkingLot.builder()
                .idParkingLot(source.idParkingLot())
                .admin(source.admin())
                .address(source.address())
                .coordX(source.coordX())
                .coordY(source.coordY())
                .name(source.name())
                .nit(source.nit())
                .phone(source.phone())
                .isActive(source.isActive())
                .build();
    }

    public static List<ParkingLotDto> mapFrom(List<ParkingLot> source) {
        return source.stream().map(ParkingLotMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<ParkingLot> mapToEntities(List<ParkingLotDto> source) {
        return source.stream().map(ParkingLotMapper::mapFrom).collect(Collectors.toList());
    }
}
