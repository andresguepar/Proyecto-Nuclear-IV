package pn.proyectonuclear4.service;

import pn.proyectonuclear4.mapping.dto.WeekDayDto;
import java.util.List;
import java.util.Optional;

public interface WeekDayService {

    List<WeekDayDto> getAllWeekDays();

    Optional<WeekDayDto> getWeekDayById(int id);

    WeekDayDto saveWeekDay(WeekDayDto weekDayDto);

}
