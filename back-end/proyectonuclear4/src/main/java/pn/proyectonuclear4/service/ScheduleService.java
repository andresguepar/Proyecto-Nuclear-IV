package pn.proyectonuclear4.service;

import pn.proyectonuclear4.mapping.dto.ScheduleDto;

import java.util.List;
import java.util.Optional;

public interface ScheduleService {

    List<ScheduleDto> getAllSchedules();

    Optional<ScheduleDto> getScheduleById(int id);

    ScheduleDto saveSchedule(ScheduleDto scheduleDto);

    void deleteSchedule(int id);

    List<ScheduleDto> getSchedulesByIsActive(Boolean isActive);

    List<ScheduleDto> getSchedulesByParkingLot(int parkingLotId);
}
