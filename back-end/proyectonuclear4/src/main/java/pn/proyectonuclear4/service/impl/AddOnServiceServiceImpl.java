package pn.proyectonuclear4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.proyectonuclear4.entity.AddOnService;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.AddOnServiceDto;
import pn.proyectonuclear4.mapping.mappers.AddOnServiceMapper;
import pn.proyectonuclear4.repository.AddOnServiceRepository;
import pn.proyectonuclear4.service.AddOnServiceService;

import java.util.List;
import java.util.Optional;

@Service
public class AddOnServiceServiceImpl implements AddOnServiceService {

    @Autowired
    private AddOnServiceRepository addOnServiceRepository;

    @Autowired
    private AddOnServiceMapper addOnServiceMapper;

    @Override
    public List<AddOnServiceDto> getAllAddOnServices() {
        List<AddOnService> addOnServices = addOnServiceRepository.findAll();
        return addOnServiceMapper.mapFrom(addOnServices);
    }

    @Override
    public Optional<AddOnServiceDto> getAddOnServiceById(int id) {
        Optional<AddOnService> addOnService = addOnServiceRepository.findById(id);
        return addOnService.map(AddOnServiceMapper::mapFrom);
    }

    @Override
    public AddOnServiceDto saveAddOnService(AddOnServiceDto addOnServiceDto) {
        AddOnService addOnServiceEntity = addOnServiceMapper.mapFrom(addOnServiceDto);
        AddOnService savedAddOnService = addOnServiceRepository.save(addOnServiceEntity);
        return addOnServiceMapper.mapFrom(savedAddOnService);
    }

    @Override
    public void deleteAddOnService(int id) {
        AddOnService addOnService = addOnServiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AddOnService not found with id: "+ id));
        addOnService.setIsActive(false);
        addOnServiceRepository.save(addOnService);
    }

    @Override
    public List<AddOnServiceDto> getAddOnServicesByIsActive(Boolean isActive) {
        List<AddOnService> addOnServices = addOnServiceRepository.findByIsActive(isActive);
        return addOnServiceMapper.mapFrom(addOnServices);
    }

    @Override
    public List<AddOnServiceDto> getAddOnServicesByParkingLotId(int parkingLotId) {
        List<AddOnService> addOnServices = addOnServiceRepository.findByParkingLotAndIsActive(parkingLotId);
        return addOnServiceMapper.mapFrom(addOnServices);
    }

}
