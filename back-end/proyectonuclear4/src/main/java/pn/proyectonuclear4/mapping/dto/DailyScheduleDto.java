package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import pn.proyectonuclear4.entity.Schedule;
import pn.proyectonuclear4.entity.WeekDay;

import java.time.LocalDateTime;

@Builder
public record DailyScheduleDto(
        int idDailySchedule,
        Schedule schedule,
        LocalDateTime startTime,
        LocalDateTime endTime,
        WeekDay weekDay,
        Boolean isActive
) {
}
