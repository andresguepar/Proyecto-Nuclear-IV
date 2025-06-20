package pn.proyectonuclear4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.proyectonuclear4.entity.ReservationAddOnService;
import pn.proyectonuclear4.entity.StandardReservation;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.ReservationAddOnServiceDto;
import pn.proyectonuclear4.mapping.mappers.ReservationAddOnServiceMapper;
import pn.proyectonuclear4.repository.ReservationAddOnServiceRepository;
import pn.proyectonuclear4.repository.StandardReservationRepository;
import pn.proyectonuclear4.service.ReservationAddOnServiceService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationAddOnServiceServiceImpl implements ReservationAddOnServiceService {

    @Autowired
    private ReservationAddOnServiceRepository reservationAddOnServiceRepository;

    @Autowired
    private StandardReservationRepository standardReservationRepository;

    @Override
    public List<ReservationAddOnServiceDto> getAllReservationAddOnServices() {
        List<ReservationAddOnService> services = reservationAddOnServiceRepository.findAll();
        return ReservationAddOnServiceMapper.mapFrom(services);
    }

    @Override
    public Optional<ReservationAddOnServiceDto> getReservationAddOnServiceById(int id) {
        Optional<ReservationAddOnService> service = reservationAddOnServiceRepository.findById(id);
        return service.map(ReservationAddOnServiceMapper::mapFrom);
    }

    @Override
    public ReservationAddOnServiceDto saveReservationAddOnService(ReservationAddOnServiceDto dto) {
        ReservationAddOnService serviceEntity = ReservationAddOnServiceMapper.mapFrom(dto);
        
        // Set timestamps
        LocalDateTime now = LocalDateTime.now();
        if (serviceEntity.getCreatedAt() == null) {
            serviceEntity.setCreatedAt(now);
        }
        serviceEntity.setUpdatedAt(now);
        
        ReservationAddOnService savedService = reservationAddOnServiceRepository.save(serviceEntity);
        return ReservationAddOnServiceMapper.mapFrom(savedService);
    }

    @Override
    public void deleteReservationAddOnService(int id) {
        ReservationAddOnService service = reservationAddOnServiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReservationAddOnService not found with id: " + id));
        service.setIsActive(false);
        service.setUpdatedAt(LocalDateTime.now());
        reservationAddOnServiceRepository.save(service);
    }

    @Override
    public List<ReservationAddOnServiceDto> getByReservationId(int reservationId) {
        List<ReservationAddOnService> services = reservationAddOnServiceRepository.findByStandardReservationIdStandardReservation(reservationId);
        return ReservationAddOnServiceMapper.mapFrom(services);
    }

    @Override
    public List<ReservationAddOnServiceDto> getActiveByReservationId(int reservationId) {
        List<ReservationAddOnService> services = reservationAddOnServiceRepository.findActiveByReservationId(reservationId);
        return ReservationAddOnServiceMapper.mapFrom(services);
    }

    @Override
    public List<ReservationAddOnServiceDto> saveMultipleForReservation(int reservationId, List<ReservationAddOnServiceDto> services) {
        StandardReservation reservation = standardReservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("StandardReservation not found with id: " + reservationId));

        LocalDateTime now = LocalDateTime.now();
        
        List<ReservationAddOnService> entities = services.stream()
                .map(dto -> {
                    ReservationAddOnService entity = ReservationAddOnServiceMapper.mapFrom(dto);
                    entity.setStandardReservation(reservation);
                    entity.setIsActive(true);
                    entity.setCreatedAt(now);
                    entity.setUpdatedAt(now);
                    return entity;
                })
                .collect(Collectors.toList());

        List<ReservationAddOnService> savedEntities = reservationAddOnServiceRepository.saveAll(entities);
        return ReservationAddOnServiceMapper.mapFrom(savedEntities);
    }
} 