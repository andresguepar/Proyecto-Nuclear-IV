package pn.proyectonuclear4.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pn.proyectonuclear4.mapping.dto.MonthlyFeeDto;
import pn.proyectonuclear4.entity.*;
import pn.proyectonuclear4.mapping.mappers.MonthlyFeeMapper;
import pn.proyectonuclear4.repository.MonthlyFeeRepository;
import pn.proyectonuclear4.service.impl.MonthlyFeeServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MonthlyFeeServiceTest {

    @Mock
    private MonthlyFeeRepository monthlyFeeRepository;

    @Mock
    private MonthlyFeeMapper monthlyFeeMapper;

    @InjectMocks
    private MonthlyFeeServiceImpl monthlyFeeService;

    private MonthlyFee monthlyFee;
    private MonthlyFeeDto monthlyFeeDto;
    private VehicleType vehicleType;
    private ParkingLot parkingLot;

    @BeforeEach
    void setUp() {
        vehicleType = VehicleType.builder()
                .idVehicleType(1)
                .name("Car")
                .isActive(true)
                .build();

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

        monthlyFee = MonthlyFee.builder()
                .idMonthlyFee(1)
                .vehicleType(vehicleType)
                .parkingLot(parkingLot)
                .price(100.0)
                .isActive(true)
                .build();

        monthlyFeeDto = new MonthlyFeeDto(
            1,
            vehicleType,
            parkingLot,
            100.0,
            true
        );
    }

    @Test
    void getAllMonthlyFees_ShouldReturnListOfMonthlyFees() {
        // Arrange
        List<MonthlyFee> monthlyFees = Arrays.asList(monthlyFee);
        List<MonthlyFeeDto> expectedDtos = Arrays.asList(monthlyFeeDto);
        when(monthlyFeeRepository.findAll()).thenReturn(monthlyFees);
        when(monthlyFeeMapper.mapFrom(any(MonthlyFee.class))).thenReturn(monthlyFeeDto);

        // Act
        List<MonthlyFeeDto> result = monthlyFeeService.getAllMonthlyFees();

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idMonthlyFee(), result.get(0).idMonthlyFee());
        verify(monthlyFeeRepository).findAll();
        verify(monthlyFeeMapper).mapFrom(any(MonthlyFee.class));
    }

    @Test
    void getMonthlyFeeById_WhenMonthlyFeeExists_ShouldReturnMonthlyFee() {
        // Arrange
        when(monthlyFeeRepository.findById(1)).thenReturn(Optional.of(monthlyFee));
        when(monthlyFeeMapper.mapFrom(monthlyFee)).thenReturn(monthlyFeeDto);

        // Act
        Optional<MonthlyFeeDto> result = monthlyFeeService.getMonthlyFeeById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(monthlyFeeDto.idMonthlyFee(), result.get().idMonthlyFee());
        assertEquals(monthlyFeeDto.price(), result.get().price());
        verify(monthlyFeeRepository).findById(1);
        verify(monthlyFeeMapper).mapFrom(monthlyFee);
    }

    @Test
    void getMonthlyFeeById_WhenMonthlyFeeDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(monthlyFeeRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<MonthlyFeeDto> result = monthlyFeeService.getMonthlyFeeById(999);

        // Assert
        assertTrue(result.isEmpty());
        verify(monthlyFeeRepository).findById(999);
        verify(monthlyFeeMapper, never()).mapFrom(any(MonthlyFee.class));
    }

    @Test
    void saveMonthlyFee_ShouldReturnSavedMonthlyFee() {
        // Arrange
        when(monthlyFeeMapper.mapFrom(any(MonthlyFeeDto.class))).thenReturn(monthlyFee);
        when(monthlyFeeRepository.save(any(MonthlyFee.class))).thenReturn(monthlyFee);
        when(monthlyFeeMapper.mapFrom(any(MonthlyFee.class))).thenReturn(monthlyFeeDto);

        // Act
        MonthlyFeeDto result = monthlyFeeService.saveMonthlyFee(monthlyFeeDto);

        // Assert
        assertNotNull(result);
        assertEquals(monthlyFeeDto.idMonthlyFee(), result.idMonthlyFee());
        assertEquals(monthlyFeeDto.price(), result.price());
        verify(monthlyFeeMapper).mapFrom(monthlyFeeDto);
        verify(monthlyFeeRepository).save(any(MonthlyFee.class));
        verify(monthlyFeeMapper).mapFrom(monthlyFee);
    }

    @Test
    void deleteMonthlyFee_WhenMonthlyFeeExists_ShouldMarkAsInactive() {
        // Arrange
        when(monthlyFeeRepository.findById(1)).thenReturn(Optional.of(monthlyFee));
        when(monthlyFeeRepository.save(any(MonthlyFee.class))).thenReturn(monthlyFee);

        // Act
        monthlyFeeService.deleteMonthlyFee(1);

        // Assert
        verify(monthlyFeeRepository).findById(1);
        verify(monthlyFeeRepository).save(any(MonthlyFee.class));
        assertFalse(monthlyFee.getIsActive());
    }

    @Test
    void deleteMonthlyFee_WhenMonthlyFeeDoesNotExist_ShouldThrowException() {
        // Arrange
        when(monthlyFeeRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> monthlyFeeService.deleteMonthlyFee(999));
        verify(monthlyFeeRepository).findById(999);
        verify(monthlyFeeRepository, never()).save(any(MonthlyFee.class));
    }

    @Test
    void getMonthlyFeesByIsActive_ShouldReturnFilteredMonthlyFees() {
        // Arrange
        List<MonthlyFee> monthlyFees = Arrays.asList(monthlyFee);
        List<MonthlyFeeDto> expectedDtos = Arrays.asList(monthlyFeeDto);
        when(monthlyFeeRepository.findByIsActive(true)).thenReturn(monthlyFees);
        when(monthlyFeeMapper.mapFrom(any(MonthlyFee.class))).thenReturn(monthlyFeeDto);

        // Act
        List<MonthlyFeeDto> result = monthlyFeeService.getMonthlyFeesByIsActive(true);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idMonthlyFee(), result.get(0).idMonthlyFee());
        verify(monthlyFeeRepository).findByIsActive(true);
        verify(monthlyFeeMapper).mapFrom(any(MonthlyFee.class));
    }
} 