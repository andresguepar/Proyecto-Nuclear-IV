package pn.proyectonuclear4.service;

import pn.proyectonuclear4.mapping.dto.MonthlyFeeDto;

import java.util.List;
import java.util.Optional;

public interface MonthlyFeeService {

    List<MonthlyFeeDto> getAllMonthlyFees();

    Optional<MonthlyFeeDto> getMonthlyFeeById(int id);

    MonthlyFeeDto saveMonthlyFee(MonthlyFeeDto monthlyFeeDto);

    void deleteMonthlyFee(int id);

    List<MonthlyFeeDto> getMonthlyFeesByIsActive(Boolean isActive);
}
