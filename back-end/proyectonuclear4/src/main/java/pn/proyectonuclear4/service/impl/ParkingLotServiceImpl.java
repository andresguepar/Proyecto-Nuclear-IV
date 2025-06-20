package pn.proyectonuclear4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.proyectonuclear4.entity.ParkingLot;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.ParkingLotDto;
import pn.proyectonuclear4.mapping.mappers.ParkingLotMapper;
import pn.proyectonuclear4.repository.ParkingLotRepository;
import pn.proyectonuclear4.repository.UserRepository;
import pn.proyectonuclear4.service.ParkingLotService;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<ParkingLotDto> getAllParkingLots() {
        List<ParkingLot> parkingLots = parkingLotRepository.findAll();
        return ParkingLotMapper.mapFrom(parkingLots);
    }

    @Override
    public Optional<ParkingLotDto> getParkingLotById(int id) {
        Optional<ParkingLot> parkingLot = parkingLotRepository.findById(id);
        return parkingLot.map(ParkingLotMapper::mapFrom);
    }

    @Override
    public ParkingLotDto saveParkingLot(ParkingLotDto parkingLotDto) {
        ParkingLot parkingLot = ParkingLotMapper.mapFrom(parkingLotDto);
        ParkingLot savedParkingLot = parkingLotRepository.save(parkingLot);
        return ParkingLotMapper.mapFrom(savedParkingLot);
    }

    @Override
    public void deleteParkingLot(int id) {
        ParkingLot parkingLot = parkingLotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking lot not found with id " + id));
        parkingLot.setIsActive(false);
        parkingLotRepository.save(parkingLot);
    }

    @Override
    public List<ParkingLotDto> getParkingLotsByIsActive(Boolean isActive) {
        List<ParkingLot> parkingLots = parkingLotRepository.findByIsActive(isActive);
        return ParkingLotMapper.mapFrom(parkingLots);
    }

    @Override
    public List<ParkingLotDto> getParkingLotsByAdminAndIsActive(int adminId, Boolean isActive) {
        List<ParkingLot> parkingLots = parkingLotRepository.findByAdminAndIsActive(userRepository.findById(adminId).orElseThrow(() -> new ResourceNotFoundException("User with id "+adminId+" not found")), isActive);
        return ParkingLotMapper.mapFrom(parkingLots);
    }
}
