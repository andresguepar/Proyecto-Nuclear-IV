package pn.proyectonuclear4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pn.proyectonuclear4.entity.DailySchedule;

import java.util.List;

@Repository
public interface DailyScheduleRepository extends JpaRepository<DailySchedule, Integer> {
    @Query("SELECT ds FROM DailySchedule ds WHERE ds.schedule.idSchedule = :scheduleId AND ds.isActive = true AND ds.weekDay.idWeekDay = :weekDayId")
    List<DailySchedule> findByWeekDayAndScheduleAndIsActive(@Param("scheduleId")int scheduleId, @Param("weekDayId")int weekDayId);

    List<DailySchedule> findByIsActive(Boolean isActive);

    @Query("SELECT ds FROM DailySchedule ds " +
           "JOIN ds.schedule s " +
           "WHERE s.parkingLot.idParkingLot = :parkingLotId " +
           "AND ds.isActive = true " +
           "ORDER BY ds.weekDay.idWeekDay, ds.startTime")
    List<DailySchedule> findByParkingLot(@Param("parkingLotId") int parkingLotId);
}
