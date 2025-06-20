package pn.proyectonuclear4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pn.proyectonuclear4.entity.MonthlyReservation;
import pn.proyectonuclear4.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MonthlyReservationRepository extends JpaRepository<MonthlyReservation, Integer> {

    List<MonthlyReservation> findByUser(User user);
    @Query("SELECT mr FROM MonthlyReservation mr " +
            "WHERE mr.slot.parkingLot.idParkingLot = :parkingLotId " +
            "AND mr.startDate <= :endDate " +
            "AND mr.endDate >= :startDate")
    List<MonthlyReservation> findByParkingLotAndDateRange(
            @Param("parkingLotId") int parkingLotId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
