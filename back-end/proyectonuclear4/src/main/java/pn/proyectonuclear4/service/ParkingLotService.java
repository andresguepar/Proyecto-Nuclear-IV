package pn.proyectonuclear4.service;

import pn.proyectonuclear4.mapping.dto.ParkingLotDto;
import java.util.List;
import java.util.Optional;

public interface ParkingLotService {

    List<ParkingLotDto> getAllParkingLots();

    Optional<ParkingLotDto> getParkingLotById(int id);

    ParkingLotDto saveParkingLot(ParkingLotDto parkingLotDto);

    void deleteParkingLot(int id);

    List<ParkingLotDto> getParkingLotsByIsActive(Boolean isActive);

    List<ParkingLotDto> getParkingLotsByAdminAndIsActive(int adminId, Boolean isActive);
}
