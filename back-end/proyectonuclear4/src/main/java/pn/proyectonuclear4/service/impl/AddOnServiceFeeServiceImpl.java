package pn.proyectonuclear4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.proyectonuclear4.entity.AddOnServiceFee;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.repository.AddOnServiceFeeRepository;
import pn.proyectonuclear4.service.AddOnServiceFeeService;
import pn.proyectonuclear4.mapping.dto.AddOnServiceFeeDto;
import pn.proyectonuclear4.mapping.mappers.AddOnServiceFeeMapper;

import java.util.List;
import java.util.Optional;

@Service
public class AddOnServiceFeeServiceImpl implements AddOnServiceFeeService {

    @Autowired
    private AddOnServiceFeeRepository addOnServiceFeeRepository;

    @Override
    public List<AddOnServiceFeeDto> getAllAddOnServiceFees() {
        List<AddOnServiceFee> fees = addOnServiceFeeRepository.findAll();
        return AddOnServiceFeeMapper.mapFrom(fees);
    }

    @Override
    public Optional<AddOnServiceFeeDto> getAddOnServiceFeeById(int id) {
        Optional<AddOnServiceFee> fee = addOnServiceFeeRepository.findById(id);
        return fee.map(AddOnServiceFeeMapper::mapFrom);
    }

    @Override
    public AddOnServiceFeeDto saveAddOnServiceFee(AddOnServiceFeeDto addOnServiceFeeDto) {
        AddOnServiceFee feeEntity = AddOnServiceFeeMapper.mapFrom(addOnServiceFeeDto);
        AddOnServiceFee savedFee = addOnServiceFeeRepository.save(feeEntity);
        return AddOnServiceFeeMapper.mapFrom(savedFee);
    }

    @Override
    public void deleteAddOnServiceFee(int id) {
        AddOnServiceFee fee = addOnServiceFeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AddOnServiceFee not found with id: " + id));
        fee.setIsActive(false);
        addOnServiceFeeRepository.save(fee);
    }

    @Override
    public List<AddOnServiceFeeDto> getAddOnServiceFeesByIsActive(Boolean isActive) {
        List<AddOnServiceFee> fees = addOnServiceFeeRepository.findByIsActive(isActive);
        return AddOnServiceFeeMapper.mapFrom(fees);
    }

}
