package pn.proyectonuclear4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pn.proyectonuclear4.entity.ReservationAddOnService;

import java.util.List;

@Repository
public interface ReservationAddOnServiceRepository extends JpaRepository<ReservationAddOnService, Integer> {
    List<ReservationAddOnService> findByStandardReservationIdStandardReservation(int reservationId);
    List<ReservationAddOnService> findByStandardReservationIdStandardReservationAndIsActive(int reservationId, Boolean isActive);
    
    @Query("SELECT raos FROM ReservationAddOnService raos WHERE raos.standardReservation.idStandardReservation = :reservationId AND raos.isActive = true")
    List<ReservationAddOnService> findActiveByReservationId(@Param("reservationId") int reservationId);
} 