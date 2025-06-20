package pn.proyectonuclear4.service;

import pn.proyectonuclear4.mapping.dto.VehicleTypeDto;

import java.util.List;
import java.util.Optional;

public interface VehicleTypeService {

    List<VehicleTypeDto> getAllVehicleTypes();

    Optional<VehicleTypeDto> getVehicleTypeById(int id);

    VehicleTypeDto saveVehicleType(VehicleTypeDto vehicleTypeDto);

    void deleteVehicleType(int id);

    List<VehicleTypeDto> getVehicleTypesByIsActive(Boolean isActive);
}
