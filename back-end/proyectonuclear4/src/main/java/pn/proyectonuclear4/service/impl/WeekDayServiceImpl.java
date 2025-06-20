package pn.proyectonuclear4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.proyectonuclear4.entity.WeekDay;
import pn.proyectonuclear4.mapping.dto.WeekDayDto;
import pn.proyectonuclear4.mapping.mappers.WeekDayMapper;
import pn.proyectonuclear4.repository.WeekDayRepository;
import pn.proyectonuclear4.service.WeekDayService;

import java.util.List;
import java.util.Optional;

@Service
public class WeekDayServiceImpl implements WeekDayService {

    @Autowired
    private WeekDayRepository weekDayRepository;

    @Override
    public List<WeekDayDto> getAllWeekDays() {
        List<WeekDay> weekDays = weekDayRepository.findAll();
        return WeekDayMapper.mapFrom(weekDays);
    }

    @Override
    public Optional<WeekDayDto> getWeekDayById(int id) {
        Optional<WeekDay> weekDay = weekDayRepository.findById(id);
        return weekDay.map(WeekDayMapper::mapFrom);
    }

    @Override
    public WeekDayDto saveWeekDay(WeekDayDto weekDayDto) {
        WeekDay weekDay = WeekDayMapper.mapFrom(weekDayDto);
        WeekDay savedWeekDay = weekDayRepository.save(weekDay);
        return WeekDayMapper.mapFrom(savedWeekDay);
    }
}
