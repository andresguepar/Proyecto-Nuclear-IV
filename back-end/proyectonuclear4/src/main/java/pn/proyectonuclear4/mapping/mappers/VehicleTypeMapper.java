package pn.proyectonuclear4.mapping.mappers;

import lombok.Builder;
import pn.proyectonuclear4.entity.VehicleType;
import pn.proyectonuclear4.mapping.dto.VehicleTypeDto;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class VehicleTypeMapper {
    public static VehicleTypeDto mapFrom(VehicleType source) {
        return VehicleTypeDto.builder()
                .idVehicleType(source.getIdVehicleType())
                .name(source.getName())
                .isActive(source.getIsActive())
                .build();
    }

    public static VehicleType mapFrom(VehicleTypeDto source) {
        return VehicleType.builder()
                .idVehicleType(source.getIdVehicleType())
                .name(source.getName())
                .isActive(source.getIsActive())
                .build();
    }

    public static List<VehicleTypeDto> mapFrom(List<VehicleType> source) {
        return source.stream().map(VehicleTypeMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<VehicleType> mapToEntities(List<VehicleTypeDto> source) {
        return source.stream().map(VehicleTypeMapper::mapFrom).collect(Collectors.toList());
    }
}
