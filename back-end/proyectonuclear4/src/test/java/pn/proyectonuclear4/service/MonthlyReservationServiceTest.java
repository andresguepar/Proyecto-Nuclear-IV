package pn.proyectonuclear4.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pn.proyectonuclear4.mapping.dto.MonthlyReservationDto;
import pn.proyectonuclear4.entity.*;
import pn.proyectonuclear4.mapping.mappers.MonthlyReservationMapper;
import pn.proyectonuclear4.repository.MonthlyReservationRepository;
import pn.proyectonuclear4.repository.UserRepository;
import pn.proyectonuclear4.service.impl.MonthlyReservationServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MonthlyReservationServiceTest {

    @Mock
    private MonthlyReservationRepository monthlyReservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MonthlyReservationMapper monthlyReservationMapper;

    @InjectMocks
    private MonthlyReservationServiceImpl monthlyReservationService;

    private MonthlyReservation monthlyReservation;
    private MonthlyReservationDto monthlyReservationDto;
    private Slot slot;
    private User user;
    private Payment payment;
    private StatusReservation statusReservation;
    private ParkingLot parkingLot;
    private VehicleType vehicleType;

    @BeforeEach
    void setUp() {
        parkingLot = ParkingLot.builder()
                .idParkingLot(1)
                .address("Test Address")
                .phone("123456789")
                .name("Test Parking Lot")
                .nit("123456789")
                .coordX("10.0")
                .coordY("20.0")
                .isActive(true)
                .build();

        vehicleType = VehicleType.builder()
                .idVehicleType(1)
                .name("Car")
                .isActive(true)
                .build();

        slot = Slot.builder()
                .idSlot(1)
                .name("A1")
                .isAvailable(true)
                .isActive(true)
                .parkingLot(parkingLot)
                .vehicleType(vehicleType)
                .build();

        user = User.builder()
                .idUser(1)
                .username("testuser")
                .password("password")
                .email("test@test.com")
                .identification("123456789")
                .phone("987654321")
                .isActive(true)
                .build();

        payment = Payment.builder()
                .idPayment(1)
                .paymentDate(LocalDateTime.now())
                .status("PAID")
                .build();

        statusReservation = StatusReservation.builder()
                .idStatusReservation(1)
                .name("ACTIVE")
                .isActive(true)
                .build();

        monthlyReservation = MonthlyReservation.builder()
                .idMonthlyReservation(1)
                .slot(slot)
                .user(user)
                .payment(payment)
                .reservationDate(LocalDateTime.now())
                .startDate(LocalDateTime.now().plusDays(1))
                .endDate(LocalDateTime.now().plusMonths(1))
                .plate("ABC123")
                .statusReservation(statusReservation)
                .build();

        monthlyReservationDto = new MonthlyReservationDto(
            1,
            slot,
            user,
            payment,
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(1),
            LocalDateTime.now().plusMonths(1),
            "ABC123",
            statusReservation
        );
    }

    @Test
    void getAllMonthlyReservations_ShouldReturnListOfMonthlyReservations() {
        // Arrange
        List<MonthlyReservation> monthlyReservations = Arrays.asList(monthlyReservation);
        List<MonthlyReservationDto> expectedDtos = Arrays.asList(monthlyReservationDto);
        when(monthlyReservationRepository.findAll()).thenReturn(monthlyReservations);
        when(monthlyReservationMapper.mapFrom(any(MonthlyReservation.class))).thenReturn(monthlyReservationDto);

        // Act
        List<MonthlyReservationDto> result = monthlyReservationService.getAllMonthlyReservations();

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idMonthlyReservation(), result.get(0).idMonthlyReservation());
        verify(monthlyReservationRepository).findAll();
        verify(monthlyReservationMapper).mapFrom(any(MonthlyReservation.class));
    }

    @Test
    void getMonthlyReservationById_WhenMonthlyReservationExists_ShouldReturnMonthlyReservation() {
        // Arrange
        when(monthlyReservationRepository.findById(1)).thenReturn(Optional.of(monthlyReservation));
        when(monthlyReservationMapper.mapFrom(monthlyReservation)).thenReturn(monthlyReservationDto);

        // Act
        Optional<MonthlyReservationDto> result = monthlyReservationService.getMonthlyReservationById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(monthlyReservationDto.idMonthlyReservation(), result.get().idMonthlyReservation());
        assertEquals(monthlyReservationDto.plate(), result.get().plate());
        verify(monthlyReservationRepository).findById(1);
        verify(monthlyReservationMapper).mapFrom(monthlyReservation);
    }

    @Test
    void getMonthlyReservationById_WhenMonthlyReservationDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(monthlyReservationRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<MonthlyReservationDto> result = monthlyReservationService.getMonthlyReservationById(999);

        // Assert
        assertTrue(result.isEmpty());
        verify(monthlyReservationRepository).findById(999);
        verify(monthlyReservationMapper, never()).mapFrom(any(MonthlyReservation.class));
    }

    @Test
    void saveMonthlyReservation_ShouldReturnSavedMonthlyReservation() {
        // Arrange
        when(monthlyReservationMapper.mapFrom(any(MonthlyReservationDto.class))).thenReturn(monthlyReservation);
        when(monthlyReservationRepository.save(any(MonthlyReservation.class))).thenReturn(monthlyReservation);
        when(monthlyReservationMapper.mapFrom(any(MonthlyReservation.class))).thenReturn(monthlyReservationDto);

        // Act
        MonthlyReservationDto result = monthlyReservationService.saveMonthlyReservation(monthlyReservationDto);

        // Assert
        assertNotNull(result);
        assertEquals(monthlyReservationDto.idMonthlyReservation(), result.idMonthlyReservation());
        assertEquals(monthlyReservationDto.plate(), result.plate());
        verify(monthlyReservationMapper).mapFrom(monthlyReservationDto);
        verify(monthlyReservationRepository).save(any(MonthlyReservation.class));
        verify(monthlyReservationMapper).mapFrom(monthlyReservation);
    }

    @Test
    void getMonthlyReservationsByUser_ShouldReturnFilteredMonthlyReservations() {
        // Arrange
        List<MonthlyReservation> monthlyReservations = Arrays.asList(monthlyReservation);
        List<MonthlyReservationDto> expectedDtos = Arrays.asList(monthlyReservationDto);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(monthlyReservationRepository.findByUser(user)).thenReturn(monthlyReservations);
        when(monthlyReservationMapper.mapFrom(any(MonthlyReservation.class))).thenReturn(monthlyReservationDto);

        // Act
        List<MonthlyReservationDto> result = monthlyReservationService.getMonthlyReservationsByUser(1);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idMonthlyReservation(), result.get(0).idMonthlyReservation());
        verify(userRepository).findById(1);
        verify(monthlyReservationRepository).findByUser(user);
        verify(monthlyReservationMapper).mapFrom(any(MonthlyReservation.class));
    }

    @Test
    void getMonthlyReservationByParkingLotIdAndDataRange_ShouldReturnFilteredMonthlyReservations() {
        // Arrange
        List<MonthlyReservation> monthlyReservations = Arrays.asList(monthlyReservation);
        List<MonthlyReservationDto> expectedDtos = Arrays.asList(monthlyReservationDto);
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusMonths(1);
        when(monthlyReservationRepository.findByParkingLotAndDateRange(1, startDate, endDate)).thenReturn(monthlyReservations);
        when(monthlyReservationMapper.mapFrom(any(MonthlyReservation.class))).thenReturn(monthlyReservationDto);

        // Act
        List<MonthlyReservationDto> result = monthlyReservationService.getMonthlyReservationByParkingLotIdAndDataRange(1, startDate, endDate);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idMonthlyReservation(), result.get(0).idMonthlyReservation());
        verify(monthlyReservationRepository).findByParkingLotAndDateRange(1, startDate, endDate);
        verify(monthlyReservationMapper).mapFrom(any(MonthlyReservation.class));
    }
} 