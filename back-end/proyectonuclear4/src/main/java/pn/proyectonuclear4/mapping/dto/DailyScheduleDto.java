package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import lombok.Data;
import pn.proyectonuclear4.entity.Schedule;
import pn.proyectonuclear4.entity.WeekDay;

import java.time.LocalTime;
@Data
@Builder
public class DailyScheduleDto{
        private int idDailySchedule;
        private Schedule schedule;
        private LocalTime startTime;
        private LocalTime endTime;
        private WeekDay weekDay;
        private Boolean isActive;
}
