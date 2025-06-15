package pn.proyectonuclear4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.proyectonuclear4.entity.DailySchedule;
import pn.proyectonuclear4.entity.Schedule;
import pn.proyectonuclear4.entity.WeekDay;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.DailyScheduleDto;
import pn.proyectonuclear4.mapping.mappers.DailyScheduleMapper;
import pn.proyectonuclear4.repository.DailyScheduleRepository;
import pn.proyectonuclear4.repository.ScheduleRepository;
import pn.proyectonuclear4.repository.WeekDayRepository;
import pn.proyectonuclear4.service.DailyScheduleService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DailyScheduleServiceImpl implements DailyScheduleService {

    @Autowired
    private DailyScheduleRepository dailyScheduleRepository;

    @Autowired
    private DailyScheduleMapper dailyScheduleMapper;

    @Override
    public List<DailyScheduleDto> getAllDailySchedules() {
        List<DailySchedule> dailySchedules = dailyScheduleRepository.findAll();
        return dailyScheduleMapper.mapFrom(dailySchedules);
    }

    @Override
    public Optional<DailyScheduleDto> getDailyScheduleById(int id) {
        Optional<DailySchedule> dailySchedule = dailyScheduleRepository.findById(id);
        return dailySchedule.map(dailyScheduleMapper::mapFrom);
    }

    @Override
    public DailyScheduleDto saveDailySchedule(DailyScheduleDto dailyScheduleDto) {
        DailySchedule dailySchedule = dailyScheduleMapper.mapFrom(dailyScheduleDto);
        
        // Asegurarse de que los tiempos estén en formato LocalTime
        if (dailyScheduleDto.startTime() != null) {
            dailySchedule.setStartTime(dailyScheduleDto.startTime());
        }
        if (dailyScheduleDto.endTime() != null) {
            dailySchedule.setEndTime(dailyScheduleDto.endTime());
        }
        
        DailySchedule savedDailySchedule = dailyScheduleRepository.save(dailySchedule);
        return dailyScheduleMapper.mapFrom(savedDailySchedule);
    }

    @Override
    public void deleteDailySchedule(int id) {
        DailySchedule dailySchedule = dailyScheduleRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("DailySchedule not found with id: "+ id));
        dailySchedule.setIsActive(false);
        dailyScheduleRepository.save(dailySchedule);
    }

    @Override
    public List<DailyScheduleDto> getDailySchedulesByIsActive(Boolean isActive) {
        List<DailySchedule> dailySchedules = dailyScheduleRepository.findByIsActive(isActive);
        return dailyScheduleMapper.mapFrom(dailySchedules);
    }

    @Override
    public List<DailyScheduleDto> getDailySchedulesByWeekDayAndScheduleAndIsActive(int scheduleId, int weekDayId, Boolean isActive) {
        List<DailySchedule> dailySchedules = dailyScheduleRepository.findByWeekDayAndScheduleAndIsActive(scheduleId, weekDayId);
        return dailyScheduleMapper.mapFrom(dailySchedules);
    }
}