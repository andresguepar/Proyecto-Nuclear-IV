package pn.proyectonuclear4.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pn.proyectonuclear4.entity.StandardReservation;
import pn.proyectonuclear4.entity.StatusReservation;
import pn.proyectonuclear4.entity.User;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.StandardReservationDto;
import pn.proyectonuclear4.mapping.mappers.StandardReservationMapper;
import pn.proyectonuclear4.repository.StandardReservationRepository;
import pn.proyectonuclear4.repository.StatusReservationRepository;
import pn.proyectonuclear4.repository.UserRepository;
import pn.proyectonuclear4.service.impl.StandardReservationServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StandardReservationServiceImplTest {

    @Mock
    private StandardReservationRepository standardReservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StatusReservationRepository statusReservationRepository;

    @InjectMocks
    private StandardReservationServiceImpl standardReservationService;

    private StandardReservation standardReservation;
    private StandardReservationDto standardReservationDto;
    private User user;
    private StatusReservation statusReservation;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .idUser(1)
                .username("Test User")
                .build();

        statusReservation = StatusReservation.builder()
                .idStatusReservation(1)
                .name("ACTIVE")
                .build();

        standardReservation = StandardReservation.builder()
                .idStandardReservation(1)
                .user(user)
                .statusReservation(statusReservation)
                .scheduledDateTime(LocalDateTime.now())
                .plate("ABC123")
                .build();

        standardReservationDto = StandardReservationMapper.mapFrom(standardReservation);
    }

    @Test
    void getAllStandardReservations_ShouldReturnAllReservations() {
        when(standardReservationRepository.findAll()).thenReturn(Arrays.asList(standardReservation));

        List<StandardReservationDto> result = standardReservationService.getAllStandardReservations();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(standardReservationRepository).findAll();
    }

    @Test
    void getStandardReservationById_WhenExists_ShouldReturnReservation() {
        when(standardReservationRepository.findById(1)).thenReturn(Optional.of(standardReservation));

        Optional<StandardReservationDto> result = standardReservationService.getStandardReservationById(1);

        assertTrue(result.isPresent());
        assertEquals(standardReservation.getIdStandardReservation(), result.get().idStandardReservation());
        verify(standardReservationRepository).findById(1);
    }

    @Test
    void getStandardReservationById_WhenNotExists_ShouldReturnEmpty() {
        when(standardReservationRepository.findById(999)).thenReturn(Optional.empty());

        Optional<StandardReservationDto> result = standardReservationService.getStandardReservationById(999);

        assertFalse(result.isPresent());
        verify(standardReservationRepository).findById(999);
    }

    @Test
    void saveStandardReservation_ShouldSaveAndReturnReservation() {
        when(standardReservationRepository.save(any(StandardReservation.class))).thenReturn(standardReservation);

        StandardReservationDto result = standardReservationService.saveStandardReservation(standardReservationDto);

        assertNotNull(result);
        assertEquals(standardReservation.getIdStandardReservation(), result.idStandardReservation());
        verify(standardReservationRepository).save(any(StandardReservation.class));
    }

    @Test
    void deleteStandardReservation_WhenExists_ShouldCancelReservation() throws ResourceNotFoundException {
        StatusReservation canceledStatus = StatusReservation.builder()
                .name("CANCELED")
                .build();

        when(standardReservationRepository.findById(1)).thenReturn(Optional.of(standardReservation));
        when(statusReservationRepository.findByName("CANCELED")).thenReturn(Optional.of(canceledStatus));
        when(standardReservationRepository.save(any(StandardReservation.class))).thenReturn(standardReservation);

        standardReservationService.deleteStandardReservation(1);

        verify(standardReservationRepository).findById(1);
        verify(statusReservationRepository).findByName("CANCELED");
        verify(standardReservationRepository).save(any(StandardReservation.class));
    }

    @Test
    void deleteStandardReservation_WhenNotExists_ShouldThrowException() {
        when(standardReservationRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> 
            standardReservationService.deleteStandardReservation(999)
        );

        verify(standardReservationRepository).findById(999);
    }

    @Test
    void getStandardReservationsByUserId_ShouldReturnUserReservations() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(standardReservationRepository.findByUser(user)).thenReturn(Arrays.asList(standardReservation));

        List<StandardReservationDto> result = standardReservationService.getStandardReservationsByUserId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository).findById(1);
        verify(standardReservationRepository).findByUser(user);
    }

    @Test
    void getStandardReservationsByParkingLotIdAndDateRange_ShouldReturnFilteredReservations() {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusDays(1);
        int parkingLotId = 1;

        when(standardReservationRepository.findByParkingLotAndDateRange(parkingLotId, startDate, endDate))
            .thenReturn(Arrays.asList(standardReservation));

        List<StandardReservationDto> result = standardReservationService
            .getStandardReservationsByParkingLotIdAndDateRange(parkingLotId, startDate, endDate);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(standardReservationRepository).findByParkingLotAndDateRange(parkingLotId, startDate, endDate);
    }

    @Test
    void updateStandardReservation_WhenExists_ShouldUpdateAndReturnReservation() throws ResourceNotFoundException {
        StandardReservationDto updateDto = new StandardReservationDto(
            1, null, null, null, LocalDateTime.now().plusDays(1), null, null, null, "XYZ789", null
        );

        when(standardReservationRepository.findById(1)).thenReturn(Optional.of(standardReservation));
        when(standardReservationRepository.save(any(StandardReservation.class))).thenReturn(standardReservation);

        StandardReservationDto result = standardReservationService.updateStandardReservation(1, updateDto);

        assertNotNull(result);
        assertEquals(standardReservation.getIdStandardReservation(), result.idStandardReservation());
        verify(standardReservationRepository).findById(1);
        verify(standardReservationRepository).save(any(StandardReservation.class));
    }

    @Test
    void updateStandardReservation_WhenNotExists_ShouldThrowException() {
        StandardReservationDto updateDto = new StandardReservationDto(
            999, null, null, null, LocalDateTime.now().plusDays(1), null, null, null, "XYZ789", null
        );

        when(standardReservationRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
            standardReservationService.updateStandardReservation(999, updateDto)
        );

        verify(standardReservationRepository).findById(999);
    }
} 