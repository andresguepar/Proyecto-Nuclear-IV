package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import pn.proyectonuclear4.entity.ParkingLot;

@Builder
public record ScheduleDto(
        int idSchedule,
        ParkingLot parkingLot,
        Boolean isActive
) {
}
