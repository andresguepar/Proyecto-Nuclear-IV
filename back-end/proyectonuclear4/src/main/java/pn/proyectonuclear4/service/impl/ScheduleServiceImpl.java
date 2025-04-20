package pn.proyectonuclear4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.proyectonuclear4.entity.Schedule;
import pn.proyectonuclear4.entity.ParkingLot;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.ScheduleDto;
import pn.proyectonuclear4.mapping.mappers.ScheduleMapper;
import pn.proyectonuclear4.repository.ScheduleRepository;
import pn.proyectonuclear4.repository.ParkingLotRepository;
import pn.proyectonuclear4.service.ScheduleService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;


    @Override
    public List<ScheduleDto> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return ScheduleMapper.mapFrom(schedules);
    }

    @Override
    public Optional<ScheduleDto> getScheduleById(int id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        return schedule.map(ScheduleMapper::mapFrom);
    }

    @Override
    public ScheduleDto saveSchedule(ScheduleDto scheduleDto) {
        Schedule schedule = ScheduleMapper.mapFrom(scheduleDto);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return ScheduleMapper.mapFrom(savedSchedule);
    }

    @Override
    public void deleteSchedule(int id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Schedule not found"));
        schedule.setIsActive(false);
        scheduleRepository.save(schedule);
    }

    @Override
    public List<ScheduleDto> getSchedulesByIsActive(Boolean isActive) {
        List<Schedule> schedules = scheduleRepository.findByIsActive(isActive);
        return ScheduleMapper.mapFrom(schedules);
    }

    @Override
    public List<ScheduleDto> getSchedulesByParkingLot(int parkingLotId) {
        List<Schedule> schedules = scheduleRepository.findByParkingLot(parkingLotRepository.findById(parkingLotId).orElseThrow(() -> new ResourceNotFoundException("Parking Lot not found")));
        return ScheduleMapper.mapFrom(schedules);
    }
}