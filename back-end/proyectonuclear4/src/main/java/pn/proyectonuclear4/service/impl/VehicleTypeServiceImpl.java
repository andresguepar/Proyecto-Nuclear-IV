package pn.proyectonuclear4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pn.proyectonuclear4.entity.VehicleType;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.VehicleTypeDto;
import pn.proyectonuclear4.mapping.mappers.VehicleTypeMapper;
import pn.proyectonuclear4.repository.VehicleTypeRepository;
import pn.proyectonuclear4.service.VehicleTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleTypeServiceImpl implements VehicleTypeService {
    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @Override
    public List<VehicleTypeDto> getAllVehicleTypes() {
        List<VehicleType> vehicleTypes = vehicleTypeRepository.findAll();
        return VehicleTypeMapper.mapFrom(vehicleTypes);
    }

    @Override
    public Optional<VehicleTypeDto> getVehicleTypeById(int id) {
        Optional<VehicleType> vehicleType = vehicleTypeRepository.findById(id);
        return vehicleType.map(VehicleTypeMapper::mapFrom);
    }

    @Override
    public VehicleTypeDto saveVehicleType(VehicleTypeDto vehicleTypeDto) {
        VehicleType vehicleType = VehicleTypeMapper.mapFrom(vehicleTypeDto);
        VehicleType savedVehicleType = vehicleTypeRepository.save(vehicleType);
        return VehicleTypeMapper.mapFrom(savedVehicleType);
    }

    @Override
    public void deleteVehicleType(int id) {
        VehicleType vehicleType = vehicleTypeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("VehicleType not found"));
        vehicleType.setIsActive(false);
        vehicleTypeRepository.save(vehicleType);
    }

    @Override
    public List<VehicleTypeDto> getVehicleTypesByIsActive(Boolean isActive) {
        List<VehicleType> vehicleTypes = vehicleTypeRepository.findByIsActive(isActive);
        return VehicleTypeMapper.mapFrom(vehicleTypes);
    }
}