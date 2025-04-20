package pn.proyectonuclear4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pn.proyectonuclear4.entity.ParkingLot;
import pn.proyectonuclear4.entity.Slot;
import pn.proyectonuclear4.entity.VehicleType;

import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Integer> {

    List<Slot> findByIsActive(Boolean isActive);

    List<Slot> findByParkingLotAndVehicleTypeAndIsAvailable(ParkingLot parkingLot, VehicleType vehicleType, Boolean isAvailable);
}
