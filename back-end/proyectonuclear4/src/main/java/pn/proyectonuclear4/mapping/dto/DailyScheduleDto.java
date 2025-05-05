package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import pn.proyectonuclear4.entity.Schedule;
import pn.proyectonuclear4.entity.WeekDay;

import java.time.LocalTime;

@Builder
public record DailyScheduleDto(
        int idDailySchedule,
        Schedule schedule,
        LocalTime startTime,
        LocalTime endTime,
        WeekDay weekDay,
        Boolean isActive
) {
}
