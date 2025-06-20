package pn.proyectonuclear4.service;

import pn.proyectonuclear4.mapping.dto.FeeDto;

import java.util.List;
import java.util.Optional;

public interface FeeService {

    List<FeeDto> getAllFees();

    Optional<FeeDto> getFeeById(int id);

    FeeDto saveFee(FeeDto feeDto);

}
