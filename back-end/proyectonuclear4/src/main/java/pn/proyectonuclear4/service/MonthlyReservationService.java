package pn.proyectonuclear4.service;

import pn.proyectonuclear4.mapping.dto.MonthlyReservationDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MonthlyReservationService {

    List<MonthlyReservationDto> getAllMonthlyReservations();

    Optional<MonthlyReservationDto> getMonthlyReservationById(int id);

    MonthlyReservationDto saveMonthlyReservation(MonthlyReservationDto monthlyReservationDto);

    void deleteMonthlyReservation(int id);

    List<MonthlyReservationDto> getMonthlyReservationByParkingLotIdAndDataRange(int parkingLotId, LocalDateTime startDate, LocalDateTime endDate);

    List<MonthlyReservationDto> getMonthlyReservationsByUser(int userId);
}
