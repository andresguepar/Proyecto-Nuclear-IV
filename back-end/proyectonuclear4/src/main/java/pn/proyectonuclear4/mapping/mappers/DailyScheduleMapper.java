package pn.proyectonuclear4.mapping.mappers;

import lombok.Builder;
import pn.proyectonuclear4.entity.DailySchedule;
import pn.proyectonuclear4.mapping.dto.DailyScheduleDto;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class DailyScheduleMapper {
    public static DailyScheduleDto mapFrom(DailySchedule source) {
        return DailyScheduleDto.builder()
                .idDailySchedule(source.getIdDailySchedule())
                .schedule(source.getSchedule())
                .startTime(source.getStartTime())
                .endTime(source.getEndTime())
                .weekDay(source.getWeekDay())
                .isActive(source.getIsActive())
                .build();
    }

    public static DailySchedule mapFrom(DailyScheduleDto source){
        return DailySchedule.builder()
                .idDailySchedule(source.getIdDailySchedule())
                .schedule(source.getSchedule())
                .startTime(source.getStartTime())
                .endTime(source.getEndTime())
                .weekDay(source.getWeekDay())
                .isActive(source.getIsActive())
                .build();
    }

    public static List<DailyScheduleDto> mapFrom(List<DailySchedule> source) {
        return source.stream().map(DailyScheduleMapper::mapFrom).collect(Collectors.toList());
    }
    public static List<DailySchedule> mapToEntities(List<DailyScheduleDto> source) {
        return source.stream().map(DailyScheduleMapper::mapFrom).collect(Collectors.toList());
    }
}
