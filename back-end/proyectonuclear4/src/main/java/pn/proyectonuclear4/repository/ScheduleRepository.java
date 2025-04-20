package pn.proyectonuclear4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pn.proyectonuclear4.entity.ParkingLot;
import pn.proyectonuclear4.entity.Schedule;

import java.util.List;

@Repository
public interface  ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findByParkingLot(ParkingLot parkingLot);
    List<Schedule> findByIsActive(Boolean isActive);
}
