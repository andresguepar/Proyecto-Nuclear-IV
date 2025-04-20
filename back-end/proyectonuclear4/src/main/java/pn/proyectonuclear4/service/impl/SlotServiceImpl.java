package pn.proyectonuclear4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.proyectonuclear4.entity.Slot;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.SlotDto;
import pn.proyectonuclear4.mapping.mappers.SlotMapper;
import pn.proyectonuclear4.repository.ParkingLotRepository;
import pn.proyectonuclear4.repository.SlotRepository;
import pn.proyectonuclear4.repository.VehicleTypeRepository;
import pn.proyectonuclear4.service.SlotService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SlotServiceImpl implements SlotService {

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @Override
    public List<SlotDto> getAllSlots() {
        List<Slot> slots = slotRepository.findAll();
        return SlotMapper.mapFrom(slots);
    }

    @Override
    public Optional<SlotDto> getSlotById(int id) {
        Optional<Slot> slot = slotRepository.findById(id);
        return slot.map(SlotMapper::mapFrom);
    }

    @Override
    public SlotDto saveSlot(SlotDto slotDto) {
        Slot slot = SlotMapper.mapFrom(slotDto);
        Slot savedSlot = slotRepository.save(slot);
        return SlotMapper.mapFrom(savedSlot);
    }

    @Override
    public void deleteSlot(int id) {
        Slot slot = slotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found"));
        slot.setIsActive(false);
        slotRepository.save(slot);

    }

    @Override
    public List<SlotDto> getSlotsByIsActive(Boolean isActive) {
        List<Slot> slots = slotRepository.findByIsActive(isActive);
        return SlotMapper.mapFrom(slots);
    }

    @Override
    public List<SlotDto> getSlotsByParkingLotIdAndVehicleTypeIdAndByIsAvailable(int parkingLotId, int vehicleTypeId, boolean isAvailable) {
        List<Slot> slots = slotRepository.findByParkingLotAndVehicleTypeAndIsAvailable(parkingLotRepository.findById(parkingLotId).orElseThrow(), vehicleTypeRepository.findById(vehicleTypeId).orElseThrow(), isAvailable);
        return SlotMapper.mapFrom(slots);
    }
}