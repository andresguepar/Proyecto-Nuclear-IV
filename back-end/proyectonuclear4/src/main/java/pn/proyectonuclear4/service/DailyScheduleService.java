package pn.proyectonuclear4.service;

import pn.proyectonuclear4.mapping.dto.DailyScheduleDto;
import java.util.List;
import java.util.Optional;

public interface DailyScheduleService {

    List<DailyScheduleDto> getAllDailySchedules();

    Optional<DailyScheduleDto> getDailyScheduleById(int id);

    DailyScheduleDto saveDailySchedule(DailyScheduleDto dailyScheduleDto);

    void deleteDailySchedule(int id);

    List<DailyScheduleDto> getDailySchedulesByIsActive(Boolean isActive);

    List<DailyScheduleDto> getDailySchedulesByWeekDayAndScheduleAndIsActive(int scheduleId, int weekDayId, Boolean isActive);
}
