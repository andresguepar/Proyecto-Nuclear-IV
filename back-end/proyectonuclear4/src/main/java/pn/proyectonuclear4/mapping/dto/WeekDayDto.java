package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;

@Builder
public record WeekDayDto(
        int idWeekDay,
        String name
) {
}
