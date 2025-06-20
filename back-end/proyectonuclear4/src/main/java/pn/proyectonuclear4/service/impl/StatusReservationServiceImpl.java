package pn.proyectonuclear4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.proyectonuclear4.entity.StatusReservation;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.StatusReservationDto;
import pn.proyectonuclear4.mapping.mappers.StatusReservationMapper;
import pn.proyectonuclear4.repository.StatusReservationRepository;
import pn.proyectonuclear4.service.StatusReservationService;

import java.util.List;
import java.util.Optional;

@Service
public class StatusReservationServiceImpl implements StatusReservationService {

    @Autowired
    private StatusReservationRepository statusReservationRepository;

    @Override
    public List<StatusReservationDto> getAllStatusReservations() {
        List<StatusReservation> statusReservations = statusReservationRepository.findAll();
        return StatusReservationMapper.mapFrom(statusReservations);
    }

    @Override
    public Optional<StatusReservationDto> getStatusReservationById(int id) {
        Optional<StatusReservation> statusReservation = statusReservationRepository.findById(id);
        return statusReservation.map(StatusReservationMapper::mapFrom);
    }

    @Override
    public StatusReservationDto saveStatusReservation(StatusReservationDto statusReservationDto) {
        StatusReservation statusReservation = StatusReservationMapper.mapFrom(statusReservationDto);
        StatusReservation savedStatusReservation = statusReservationRepository.save(statusReservation);
        return StatusReservationMapper.mapFrom(savedStatusReservation);
    }

    @Override
    public void deleteStatusReservation(int id) {
        StatusReservation statusReservation = statusReservationRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No se puede eliminar el status"));
        statusReservation.setIsActive(false);
        statusReservationRepository.save(statusReservation);
    }

    @Override
    public List<StatusReservationDto> getStatusReservationsByIsActive(Boolean isActive) {
        List<StatusReservation> statusReservations = statusReservationRepository.findByIsActive(isActive);
        return StatusReservationMapper.mapFrom(statusReservations);
    }
}
