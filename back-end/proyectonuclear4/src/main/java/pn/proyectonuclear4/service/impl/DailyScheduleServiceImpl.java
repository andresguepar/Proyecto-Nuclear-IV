package pn.proyectonuclear4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.proyectonuclear4.entity.DailySchedule;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.DailyScheduleDto;
import pn.proyectonuclear4.mapping.mappers.DailyScheduleMapper;
import pn.proyectonuclear4.repository.DailyScheduleRepository;
import pn.proyectonuclear4.service.DailyScheduleService;

import java.util.List;
import java.util.Optional;

@Service
public class DailyScheduleServiceImpl implements DailyScheduleService {

    @Autowired
    private DailyScheduleRepository dailyScheduleRepository;


    @Override
    public List<DailyScheduleDto> getAllDailySchedules() {
        List<DailySchedule> dailySchedules = dailyScheduleRepository.findAll();
        return DailyScheduleMapper.mapFrom(dailySchedules);
    }

    @Override
    public Optional<DailyScheduleDto> getDailyScheduleById(int id) {
        Optional<DailySchedule> dailySchedule = dailyScheduleRepository.findById(id);
        return dailySchedule.map(DailyScheduleMapper::mapFrom);
    }

    @Override
    public DailyScheduleDto saveDailySchedule(DailyScheduleDto dailyScheduleDto) {
        DailySchedule dailySchedule = DailyScheduleMapper.mapFrom(dailyScheduleDto);

        // Asegurarse de que los tiempos estén en formato LocalTime
        if (dailyScheduleDto.getStartTime() != null) {
            dailySchedule.setStartTime(dailyScheduleDto.getStartTime());
        }
        if (dailyScheduleDto.getEndTime() != null) {
            dailySchedule.setEndTime(dailyScheduleDto.getEndTime());
        }

        DailySchedule savedDailySchedule = dailyScheduleRepository.save(dailySchedule);
        return DailyScheduleMapper.mapFrom(savedDailySchedule);
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
        return DailyScheduleMapper.mapFrom(dailySchedules);
    }

    @Override
    public List<DailyScheduleDto> getDailySchedulesByWeekDayAndScheduleAndIsActive(int scheduleId, int weekDayId, Boolean isActive) {
        List<DailySchedule> dailySchedules = dailyScheduleRepository.findByWeekDayAndScheduleAndIsActive(scheduleId, weekDayId);
        return DailyScheduleMapper.mapFrom(dailySchedules);
    }

    @Override
    public List<DailyScheduleDto> getDailySchedulesByParkingLot(int parkingLotId) {
        List<DailySchedule> dailySchedules = dailyScheduleRepository.findByParkingLot(parkingLotId);
        return DailyScheduleMapper.mapFrom(dailySchedules);
    }
}
