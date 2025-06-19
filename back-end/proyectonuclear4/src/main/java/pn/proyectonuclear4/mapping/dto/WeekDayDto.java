package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeekDayDto{
        private int idWeekDay;
        private String name;
}
