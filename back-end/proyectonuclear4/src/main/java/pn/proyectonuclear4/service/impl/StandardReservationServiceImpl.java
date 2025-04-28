package pn.proyectonuclear4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.proyectonuclear4.entity.StandardReservation;
import pn.proyectonuclear4.entity.User;
import pn.proyectonuclear4.entity.Slot;
import pn.proyectonuclear4.entity.StatusReservation;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.StandardReservationDto;
import pn.proyectonuclear4.mapping.mappers.StandardFeeMapper;
import pn.proyectonuclear4.mapping.mappers.StandardReservationMapper;
import pn.proyectonuclear4.repository.StandardReservationRepository;
import pn.proyectonuclear4.repository.UserRepository;
import pn.proyectonuclear4.repository.SlotRepository;
import pn.proyectonuclear4.repository.StatusReservationRepository;
import pn.proyectonuclear4.service.StandardReservationService;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StandardReservationServiceImpl implements StandardReservationService {

    @Autowired
    private StandardReservationRepository standardReservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusReservationRepository statusReservationRepository;


    @Override
    public List<StandardReservationDto> getAllStandardReservations() {
        List<StandardReservation> standardReservations = standardReservationRepository.findAll();
        return StandardReservationMapper.mapFrom(standardReservations);
    }

    @Override
    public Optional<StandardReservationDto> getStandardReservationById(int id) {
        Optional<StandardReservation> standardReservation = standardReservationRepository.findById(id);
        return standardReservation.map(StandardReservationMapper::mapFrom);
    }

    @Override
    public StandardReservationDto saveStandardReservation(StandardReservationDto standardReservationDto) {
        StandardReservation standardReservation = StandardReservationMapper.mapFrom(standardReservationDto);
        StandardReservation savedStandardReservation = standardReservationRepository.save(standardReservation);
        return StandardReservationMapper.mapFrom(savedStandardReservation);
    }

    @Override
    public void deleteStandardReservation(int id) throws ResourceNotFoundException {
        StandardReservation standardReservation = standardReservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Standard reservation not found with id: " + id));

        StatusReservation canceledStatus = statusReservationRepository.findByName("CANCELED")
                .orElseThrow(() -> new ResourceNotFoundException("Status 'CANCELED' not found"));

        standardReservation.setStatusReservation(canceledStatus);
        standardReservationRepository.save(standardReservation);
    }

    @Override
    public List<StandardReservationDto> getStandardReservationsByUserId(int userId) {
        List<StandardReservation> standardReservations = standardReservationRepository.findByUser(userRepository.findById(userId).orElseThrow());
        return StandardReservationMapper.mapFrom(standardReservations);
    }

    @Override
    public List<StandardReservationDto> getStandardReservationsByParkingLotIdAndDateRange(int parkingLotId, LocalDateTime startDate, LocalDateTime endDate) {
        List<StandardReservation> standardReservations = standardReservationRepository.findByParkingLotAndDateRange(parkingLotId, startDate, endDate);
        return StandardReservationMapper.mapFrom(standardReservations);
    }

    @Override
    public StandardReservationDto updateStandardReservation(int id, StandardReservationDto standardReservationDto) throws ResourceNotFoundException {
        StandardReservation existingReservation = standardReservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Standard reservation not found with id: " + id));

        // Update only the allowed fields
        if (standardReservationDto.scheduledDateTime() != null) {
            existingReservation.setScheduledDateTime(standardReservationDto.scheduledDateTime());
        }
        if (standardReservationDto.plate() != null) {
            existingReservation.setPlate(standardReservationDto.plate());
        }
        if (standardReservationDto.slot() != null) {
            existingReservation.setSlot(standardReservationDto.slot());
        }

        StandardReservation updatedReservation = standardReservationRepository.save(existingReservation);
        return StandardReservationMapper.mapFrom(updatedReservation);
    }
}
