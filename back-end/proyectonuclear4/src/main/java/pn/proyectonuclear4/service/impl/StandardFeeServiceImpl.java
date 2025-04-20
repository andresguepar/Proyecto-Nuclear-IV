package pn.proyectonuclear4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.proyectonuclear4.entity.StandardFee;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.StandardFeeDto;
import pn.proyectonuclear4.mapping.mappers.StandardFeeMapper;
import pn.proyectonuclear4.repository.StandardFeeRepository;
import pn.proyectonuclear4.service.StandardFeeService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StandardFeeServiceImpl implements StandardFeeService {

    @Autowired
    private StandardFeeRepository standardFeeRepository;


    @Override
    public List<StandardFeeDto> getAllStandardFees() {
        List<StandardFee> standardFees = standardFeeRepository.findAll();
        return StandardFeeMapper.mapFrom(standardFees);
    }

    @Override
    public Optional<StandardFeeDto> getStandardFeeById(int id) {
        Optional<StandardFee> standardFee = standardFeeRepository.findById(id);
        return standardFee.map(StandardFeeMapper::mapFrom);
    }

    @Override
    public StandardFeeDto saveStandardFee(StandardFeeDto standardFeeDto) {
        StandardFee standardFee = StandardFeeMapper.mapFrom(standardFeeDto);
        StandardFee savedStandardFee = standardFeeRepository.save(standardFee);
        return StandardFeeMapper.mapFrom(savedStandardFee);
    }

    @Override
    public void deleteStandardFee(int id) {
        StandardFee standardFee = standardFeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Standard fee not found"));
        standardFee.setIsActive(false);
        standardFeeRepository.save(standardFee);
    }

    @Override
    public List<StandardFeeDto> getStandardFeesByIsActive(Boolean isActive) {
        List<StandardFee> standardFees = standardFeeRepository.findByIsActive(isActive);
        return StandardFeeMapper.mapFrom(standardFees);
    }
}