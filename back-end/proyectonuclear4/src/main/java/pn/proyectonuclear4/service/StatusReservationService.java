package pn.proyectonuclear4.service;

import pn.proyectonuclear4.mapping.dto.StatusReservationDto;

import java.util.List;
import java.util.Optional;

public interface StatusReservationService {

    List<StatusReservationDto> getAllStatusReservations();

    Optional<StatusReservationDto> getStatusReservationById(int id);

    StatusReservationDto saveStatusReservation(StatusReservationDto statusReservationDto);

    void deleteStatusReservation(int id);

    List<StatusReservationDto> getStatusReservationsByIsActive(Boolean isActive);

}
