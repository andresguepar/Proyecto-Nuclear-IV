package pn.proyectonuclear4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pn.proyectonuclear4.entity.StandardReservation;
import pn.proyectonuclear4.entity.StatusReservation;
import pn.proyectonuclear4.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StandardReservationRepository extends JpaRepository<StandardReservation, Integer> {
    List<StandardReservation> findByUserAndStatusReservation(User user, StatusReservation statusReservation);

    List<StandardReservation> findByUser(User user);
    @Query("SELECT sr FROM StandardReservation sr " +
            "WHERE sr.slot.parkingLot.idParkingLot = :parkingLotId " +
            "AND sr.scheduledDateTime < :endDate " +
            "AND sr.scheduledDateTime > :startDate")
    List<StandardReservation> findByParkingLotAndDateRange(
            @Param("parkingLotId") int parkingLotId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT sr FROM StandardReservation sr " +
            "WHERE sr.slot.parkingLot.admin.idUser = :adminId")
    List<StandardReservation> findByParkingLotAdmin(@Param("adminId") int adminId);

    @Query("SELECT sr FROM StandardReservation sr " +
            "JOIN sr.slot s " +
            "JOIN s.parkingLot pl " +
            "WHERE pl.admin.idUser = :adminId")
    List<StandardReservation> findByParkingLotAdminDebug(@Param("adminId") int adminId);

    @Query("SELECT COUNT(pl) FROM ParkingLot pl WHERE pl.admin.idUser = :adminId")
    int countParkingLotsByAdmin(@Param("adminId") int adminId);
}
