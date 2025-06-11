package pn.proyectonuclear4.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pn.proyectonuclear4.entity.ParkingLot;
import pn.proyectonuclear4.entity.User;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.ParkingLotDto;
import pn.proyectonuclear4.mapping.mappers.ParkingLotMapper;
import pn.proyectonuclear4.repository.ParkingLotRepository;
import pn.proyectonuclear4.repository.UserRepository;
import pn.proyectonuclear4.service.impl.ParkingLotServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParkingLotServiceImplTest {

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ParkingLotServiceImpl parkingLotService;

    private ParkingLot parkingLot;
    private ParkingLotDto parkingLotDto;
    private User admin;

    @BeforeEach
    void setUp() {
        admin = User.builder()
                .idUser(1)
                .username("Admin User")
                .build();

        parkingLot = ParkingLot.builder()
                .idParkingLot(1)
                .name("Test Parking Lot")
                .address("123 Test St")
                .isActive(true)
                .admin(admin)
                .build();

        parkingLotDto = ParkingLotMapper.mapFrom(parkingLot);
    }

    @Test
    void getAllParkingLots_ShouldReturnAllParkingLots() {
        when(parkingLotRepository.findAll()).thenReturn(Arrays.asList(parkingLot));

        List<ParkingLotDto> result = parkingLotService.getAllParkingLots();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(parkingLotRepository).findAll();
    }

    @Test
    void getParkingLotById_WhenExists_ShouldReturnParkingLot() {
        when(parkingLotRepository.findById(1)).thenReturn(Optional.of(parkingLot));

        Optional<ParkingLotDto> result = parkingLotService.getParkingLotById(1);

        assertTrue(result.isPresent());
        assertEquals(parkingLot.getIdParkingLot(), result.get().idParkingLot());
        verify(parkingLotRepository).findById(1);
    }

    @Test
    void getParkingLotById_WhenNotExists_ShouldReturnEmpty() {
        when(parkingLotRepository.findById(999)).thenReturn(Optional.empty());

        Optional<ParkingLotDto> result = parkingLotService.getParkingLotById(999);

        assertFalse(result.isPresent());
        verify(parkingLotRepository).findById(999);
    }

    @Test
    void saveParkingLot_ShouldSaveAndReturnParkingLot() {
        when(parkingLotRepository.save(any(ParkingLot.class))).thenReturn(parkingLot);

        ParkingLotDto result = parkingLotService.saveParkingLot(parkingLotDto);

        assertNotNull(result);
        assertEquals(parkingLot.getIdParkingLot(), result.idParkingLot());
        verify(parkingLotRepository).save(any(ParkingLot.class));
    }

    @Test
    void deleteParkingLot_WhenExists_ShouldMarkAsInactive() {
        when(parkingLotRepository.findById(1)).thenReturn(Optional.of(parkingLot));
        when(parkingLotRepository.save(any(ParkingLot.class))).thenReturn(parkingLot);

        parkingLotService.deleteParkingLot(1);

        verify(parkingLotRepository).findById(1);
        verify(parkingLotRepository).save(any(ParkingLot.class));
    }

    @Test
    void deleteParkingLot_WhenNotExists_ShouldThrowException() {
        when(parkingLotRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
            parkingLotService.deleteParkingLot(999)
        );

        verify(parkingLotRepository).findById(999);
    }

    @Test
    void getParkingLotsByIsActive_ShouldReturnFilteredParkingLots() {
        when(parkingLotRepository.findByIsActive(true)).thenReturn(Arrays.asList(parkingLot));

        List<ParkingLotDto> result = parkingLotService.getParkingLotsByIsActive(true);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(parkingLotRepository).findByIsActive(true);
    }

    @Test
    void getParkingLotsByAdminAndIsActive_ShouldReturnFilteredParkingLots() {
        when(userRepository.findById(1)).thenReturn(Optional.of(admin));
        when(parkingLotRepository.findByAdminAndIsActive(admin, true)).thenReturn(Arrays.asList(parkingLot));

        List<ParkingLotDto> result = parkingLotService.getParkingLotsByAdminAndIsActive(1, true);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository).findById(1);
        verify(parkingLotRepository).findByAdminAndIsActive(admin, true);
    }

    @Test
    void getParkingLotsByAdminAndIsActive_WhenAdminNotExists_ShouldThrowException() {
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
            parkingLotService.getParkingLotsByAdminAndIsActive(999, true)
        );

        verify(userRepository).findById(999);
    }
} 