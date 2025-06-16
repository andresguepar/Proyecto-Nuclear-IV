package pn.proyectonuclear4.mapping.mappers;

import pn.proyectonuclear4.entity.ParkingLot;
import pn.proyectonuclear4.mapping.dto.ParkingLotDto;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class ParkingLotMapper {
    public static ParkingLotDto mapFrom(ParkingLot source) {
        return ParkingLotDto.builder()
                .idParkingLot(source.getIdParkingLot())
                .admin(source.getAdmin())
                .address(source.getAddress())
                .coordX(source.getCoordX())
                .coordY(source.getCoordY())
                .name(source.getName())
                .nit(source.getNit())
                .phone(source.getPhone())
                .isActive(source.getIsActive())
                .build();
    }

    public static ParkingLot mapFrom(ParkingLotDto source) {
        return ParkingLot.builder()
                .idParkingLot(source.getIdParkingLot())
                .admin(source.getAdmin())
                .address(source.getAddress())
                .coordX(source.getCoordX())
                .coordY(source.getCoordY())
                .name(source.getName())
                .nit(source.getNit())
                .phone(source.getPhone())
                .isActive(source.getIsActive())
                .build();
    }

    public static List<ParkingLotDto> mapFrom(List<ParkingLot> source) {
        return source.stream().map(ParkingLotMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<ParkingLot> mapToEntities(List<ParkingLotDto> source) {
        return source.stream().map(ParkingLotMapper::mapFrom).collect(Collectors.toList());
    }
}
