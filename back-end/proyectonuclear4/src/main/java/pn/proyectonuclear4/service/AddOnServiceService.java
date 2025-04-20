package pn.proyectonuclear4.service;

import pn.proyectonuclear4.mapping.dto.AddOnServiceDto;
import java.util.List;
import java.util.Optional;

public interface AddOnServiceService {

    List<AddOnServiceDto> getAllAddOnServices();

    Optional<AddOnServiceDto> getAddOnServiceById(int id);

    AddOnServiceDto saveAddOnService(AddOnServiceDto addOnServiceDto);

    void deleteAddOnService(int id);

    List<AddOnServiceDto> getAddOnServicesByIsActive(Boolean isActive);

    List<AddOnServiceDto> getAddOnServicesByParkingLotId(int parkingLotId);
}
