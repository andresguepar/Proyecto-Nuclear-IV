package pn.proyectonuclear4.service;

import pn.proyectonuclear4.mapping.dto.StandardReservationDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StandardReservationService {

    List<StandardReservationDto> getAllStandardReservations();

    Optional<StandardReservationDto> getStandardReservationById(int id);

    StandardReservationDto saveStandardReservation(StandardReservationDto standardReservationDto);

    void deleteStandardReservation(int id);

    List<StandardReservationDto> getStandardReservationsByUserId(int userId);

    List<StandardReservationDto> getStandardReservationsByParkingLotIdAndDateRange(int parkingLotId, LocalDateTime startDate, LocalDateTime endDate);
}
