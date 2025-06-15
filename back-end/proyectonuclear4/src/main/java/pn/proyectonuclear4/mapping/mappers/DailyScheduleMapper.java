package pn.proyectonuclear4.mapping.mappers;

import pn.proyectonuclear4.entity.DailySchedule;
import pn.proyectonuclear4.mapping.dto.DailyScheduleDto;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DailyScheduleMapper {
    public DailyScheduleDto mapFrom(DailySchedule source) {
        if (source == null) {
            return null;
        }
        return new DailyScheduleDto(
            source.getIdDailySchedule(),
            source.getSchedule(),
            source.getStartTime(),
            source.getEndTime(),
            source.getWeekDay(),
            source.getIsActive()
        );
    }

    public DailySchedule mapFrom(DailyScheduleDto source) {
        if (source == null) {
            return null;
        }
        return DailySchedule.builder()
                .idDailySchedule(source.idDailySchedule())
                .schedule(source.schedule())
                .startTime(source.startTime())
                .endTime(source.endTime())
                .weekDay(source.weekDay())
                .isActive(source.isActive())
                .build();
    }

    public List<DailyScheduleDto> mapFrom(List<DailySchedule> source) {
        if (source == null) {
            return Collections.emptyList();
        }
        return source.stream()
                .map(this::mapFrom)
                .collect(Collectors.toList());
    }

    public List<DailySchedule> mapToEntities(List<DailyScheduleDto> source) {
        if (source == null) {
            return Collections.emptyList();
        }
        return source.stream()
                .map(this::mapFrom)
                .collect(Collectors.toList());
    }
}
