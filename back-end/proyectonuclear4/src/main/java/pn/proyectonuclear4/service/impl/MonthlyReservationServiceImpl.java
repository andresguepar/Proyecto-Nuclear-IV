package pn.proyectonuclear4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.proyectonuclear4.entity.MonthlyReservation;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.MonthlyReservationDto;
import pn.proyectonuclear4.mapping.mappers.MonthlyReservationMapper;
import pn.proyectonuclear4.repository.MonthlyReservationRepository;
import pn.proyectonuclear4.repository.UserRepository;
import pn.proyectonuclear4.service.MonthlyReservationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MonthlyReservationServiceImpl implements MonthlyReservationService {

    @Autowired
    private MonthlyReservationRepository monthlyReservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<MonthlyReservationDto> getAllMonthlyReservations() {
        List<MonthlyReservation> monthlyReservations = monthlyReservationRepository.findAll();
        return MonthlyReservationMapper.mapFrom(monthlyReservations);
    }

    @Override
    public Optional<MonthlyReservationDto> getMonthlyReservationById(int id) {
        Optional<MonthlyReservation> monthlyReservation = monthlyReservationRepository.findById(id);
        return monthlyReservation.map(MonthlyReservationMapper::mapFrom);
    }

    @Override
    public MonthlyReservationDto saveMonthlyReservation(MonthlyReservationDto monthlyReservationDto) {
        MonthlyReservation monthlyReservation = MonthlyReservationMapper.mapFrom(monthlyReservationDto);
        MonthlyReservation savedMonthlyReservation = monthlyReservationRepository.save(monthlyReservation);
        return MonthlyReservationMapper.mapFrom(savedMonthlyReservation);
    }

    @Override
    public void deleteMonthlyReservation(int id) {
        MonthlyReservation monthlyReservation = monthlyReservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monthly reservation with id " + id + " not found"));
        monthlyReservation.getStatusReservation().setIdStatusReservation(1);
        monthlyReservationRepository.save(monthlyReservation);

    }

    @Override
    public List<MonthlyReservationDto> getMonthlyReservationByParkingLotIdAndDataRange(int parkingLotId, LocalDateTime startDate, LocalDateTime endDate) {
        List<MonthlyReservation> monthlyReservations = monthlyReservationRepository.findByParkingLotAndDateRange(parkingLotId, startDate, endDate);
        return MonthlyReservationMapper.mapFrom(monthlyReservations);
    }

    @Override
    public List<MonthlyReservationDto> getMonthlyReservationsByUser(int userId) {
        List<MonthlyReservation> monthlyReservations = monthlyReservationRepository.findByUser(userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found")));
        return MonthlyReservationMapper.mapFrom(monthlyReservations);
    }

}
