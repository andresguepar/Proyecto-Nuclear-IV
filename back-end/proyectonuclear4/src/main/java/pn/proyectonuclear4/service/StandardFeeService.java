package pn.proyectonuclear4.service;

import pn.proyectonuclear4.mapping.dto.StandardFeeDto;
import java.util.List;
import java.util.Optional;

public interface StandardFeeService {

    List<StandardFeeDto> getAllStandardFees();

    Optional<StandardFeeDto> getStandardFeeById(int id);

    StandardFeeDto saveStandardFee(StandardFeeDto standardFeeDto);

    void deleteStandardFee(int id);

    List<StandardFeeDto> getStandardFeesByIsActive(Boolean isActive);
}
