package pn.proyectonuclear4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.proyectonuclear4.entity.MonthlyFee;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.MonthlyFeeDto;
import pn.proyectonuclear4.mapping.mappers.MonthlyFeeMapper;
import pn.proyectonuclear4.repository.MonthlyFeeRepository;
import pn.proyectonuclear4.service.MonthlyFeeService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MonthlyFeeServiceImpl implements MonthlyFeeService {

    @Autowired
    private MonthlyFeeRepository monthlyFeeRepository;


    @Override
    public List<MonthlyFeeDto> getAllMonthlyFees() {
        List<MonthlyFee> monthlyFees = monthlyFeeRepository.findAll();
        return MonthlyFeeMapper.mapFrom(monthlyFees);
    }

    @Override
    public Optional<MonthlyFeeDto> getMonthlyFeeById(int id) {
        Optional<MonthlyFee> monthlyFee = monthlyFeeRepository.findById(id);
        return monthlyFee.map(MonthlyFeeMapper::mapFrom);
    }

    @Override
    public MonthlyFeeDto saveMonthlyFee(MonthlyFeeDto monthlyFeeDto) {
        MonthlyFee monthlyFeeEntity = MonthlyFeeMapper.mapFrom(monthlyFeeDto);
        MonthlyFee savedMonthlyFee = monthlyFeeRepository.save(monthlyFeeEntity);
        return MonthlyFeeMapper.mapFrom(savedMonthlyFee);
    }

    @Override
    public void deleteMonthlyFee(int id) {
        MonthlyFee monthlyFee = monthlyFeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monthly fee not found with id: " + id));
        monthlyFee.setIsActive(false);
        monthlyFeeRepository.save(monthlyFee);
    }

    @Override
    public List<MonthlyFeeDto> getMonthlyFeesByIsActive(Boolean isActive) {
        List<MonthlyFee> monthlyFees = monthlyFeeRepository.findByIsActive(isActive);
        return MonthlyFeeMapper.mapFrom(monthlyFees);
    }
}