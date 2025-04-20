package pn.proyectonuclear4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.proyectonuclear4.entity.Fee;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.FeeDto;
import pn.proyectonuclear4.mapping.mappers.FeeMapper;
import pn.proyectonuclear4.repository.FeeRepository;
import pn.proyectonuclear4.service.FeeService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FeeServiceImpl implements FeeService {

    @Autowired
    private FeeRepository feeRepository;

    @Override
    public List<FeeDto> getAllFees() {
        List<Fee> feeList = feeRepository.findAll();
        return FeeMapper.mapFrom(feeList);
    }

    @Override
    public Optional<FeeDto> getFeeById(int id) {
        Optional<Fee> fee = feeRepository.findById(id);
        return fee.map(FeeMapper::mapFrom);
    }

    @Override
    public FeeDto saveFee(FeeDto feeDto) {
        Fee fee = FeeMapper.mapFrom(feeDto);
        Fee saved = feeRepository.save(fee);
        return FeeMapper.mapFrom(saved);
    }
}