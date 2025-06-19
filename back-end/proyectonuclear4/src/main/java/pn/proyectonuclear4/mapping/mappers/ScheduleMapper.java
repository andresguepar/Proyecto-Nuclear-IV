package pn.proyectonuclear4.mapping.mappers;

import pn.proyectonuclear4.entity.Schedule;
import pn.proyectonuclear4.mapping.dto.ScheduleDto;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class ScheduleMapper {
    public static ScheduleDto mapFrom(Schedule source) {
        return ScheduleDto.builder()
                .idSchedule(source.getIdSchedule())
                .parkingLot(source.getParkingLot())
                .isActive(source.getIsActive())
                .build();
    }

    public static Schedule mapFrom(ScheduleDto source) {
        return Schedule.builder()
                .idSchedule(source.getIdSchedule())
                .parkingLot(source.getParkingLot())
                .isActive(source.getIsActive())
                .build();
    }

    public static List<ScheduleDto> mapFrom(List<Schedule> source) {
        return source.stream().map(ScheduleMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<Schedule> mapToEntities(List<ScheduleDto> source) {
        return source.stream().map(ScheduleMapper::mapFrom).collect(Collectors.toList());
    }
}
