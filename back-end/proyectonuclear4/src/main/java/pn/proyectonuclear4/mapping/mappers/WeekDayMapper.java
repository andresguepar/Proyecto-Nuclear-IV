package pn.proyectonuclear4.mapping.mappers;

import pn.proyectonuclear4.entity.WeekDay;
import pn.proyectonuclear4.mapping.dto.WeekDayDto;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class WeekDayMapper {
    public static WeekDayDto mapFrom(WeekDay source) {
        return WeekDayDto.builder()
                .idWeekDay(source.getIdWeekDay())
                .name(source.getName())
                .build();
    }

    public static WeekDay mapFrom(WeekDayDto source) {
        return WeekDay.builder()
                .idWeekDay(source.getIdWeekDay())
                .name(source.getName())
                .build();
    }

    public static List<WeekDayDto> mapFrom(List<WeekDay> source) {
        return source.stream().map(WeekDayMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<WeekDay> mapToEntities(List<WeekDayDto> source) {
        return source.stream().map(WeekDayMapper::mapFrom).collect(Collectors.toList());
    }
}
