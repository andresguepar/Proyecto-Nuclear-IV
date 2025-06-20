package pn.proyectonuclear4.mapping.mappers;

import lombok.Builder;
import pn.proyectonuclear4.entity.Slot;
import pn.proyectonuclear4.mapping.dto.SlotDto;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class SlotMapper {
    public static SlotDto mapFrom(Slot source) {
        return SlotDto.builder()
                .idSlot(source.getIdSlot())
                .name(source.getName())
                .isAvailable(source.getIsAvailable())
                .isActive(source.getIsActive())
                .parkingLot(source.getParkingLot())
                .vehicleType(source.getVehicleType())
                .build();
    }

    public static Slot mapFrom(SlotDto source) {
        return Slot.builder()
                .idSlot(source.getIdSlot())
                .name(source.getName())
                .isAvailable(source.getIsAvailable())
                .isActive(source.getIsActive())
                .parkingLot(source.getParkingLot())
                .vehicleType(source.getVehicleType())
                .build();
    }

    public static List<SlotDto> mapFrom(List<Slot> source) {
        return source.stream().map(SlotMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<Slot> mapToEntities(List<SlotDto> source) {
        return source.stream().map(SlotMapper::mapFrom).collect(Collectors.toList());
    }
}
