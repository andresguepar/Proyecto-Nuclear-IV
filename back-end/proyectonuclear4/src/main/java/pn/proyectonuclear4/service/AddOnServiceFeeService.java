package pn.proyectonuclear4.service;

import pn.proyectonuclear4.mapping.dto.AddOnServiceFeeDto;
import java.util.List;
import java.util.Optional;

public interface AddOnServiceFeeService {

    List<AddOnServiceFeeDto> getAllAddOnServiceFees();

    Optional<AddOnServiceFeeDto> getAddOnServiceFeeById(int id);

    AddOnServiceFeeDto saveAddOnServiceFee(AddOnServiceFeeDto addOnServiceFeeDto);

    List<AddOnServiceFeeDto> getAddOnServiceFeesByIsActive(Boolean isActive);

    void deleteAddOnServiceFee(int id);

}
