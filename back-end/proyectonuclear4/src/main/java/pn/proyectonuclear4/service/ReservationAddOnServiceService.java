package pn.proyectonuclear4.service;

import pn.proyectonuclear4.mapping.dto.ReservationAddOnServiceDto;

import java.util.List;
import java.util.Optional;

public interface ReservationAddOnServiceService {
    List<ReservationAddOnServiceDto> getAllReservationAddOnServices();
    Optional<ReservationAddOnServiceDto> getReservationAddOnServiceById(int id);
    ReservationAddOnServiceDto saveReservationAddOnService(ReservationAddOnServiceDto dto);
    void deleteReservationAddOnService(int id);
    List<ReservationAddOnServiceDto> getByReservationId(int reservationId);
    List<ReservationAddOnServiceDto> getActiveByReservationId(int reservationId);
    List<ReservationAddOnServiceDto> saveMultipleForReservation(int reservationId, List<ReservationAddOnServiceDto> services);
}
