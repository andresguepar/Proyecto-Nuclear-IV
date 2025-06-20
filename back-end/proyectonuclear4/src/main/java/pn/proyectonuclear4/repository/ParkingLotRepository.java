package pn.proyectonuclear4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pn.proyectonuclear4.entity.ParkingLot;
import pn.proyectonuclear4.entity.User;

import java.util.List;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Integer> {
    List<ParkingLot> findByIsActive(Boolean isActive);

    List<ParkingLot> findByAdminAndIsActive(User admin, Boolean isActive);
}
