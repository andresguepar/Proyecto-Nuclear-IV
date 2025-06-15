package pn.proyectonuclear4.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pn.proyectonuclear4.entity.AddOnService;
import pn.proyectonuclear4.entity.ParkingLot;
import pn.proyectonuclear4.mapping.dto.AddOnServiceDto;
import pn.proyectonuclear4.mapping.mappers.AddOnServiceMapper;
import pn.proyectonuclear4.repository.AddOnServiceRepository;
import pn.proyectonuclear4.service.impl.AddOnServiceServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddOnServiceTest {

    @Mock
    private AddOnServiceRepository addOnServiceRepository;

    @Mock
    private AddOnServiceMapper addOnServiceMapper;

    @InjectMocks
    private AddOnServiceServiceImpl addOnService;

    private AddOnService addOnServiceEntity;
    private AddOnServiceDto addOnServiceDto;
    private ParkingLot parkingLot;

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

        addOnServiceEntity = AddOnService.builder()
                .idAddOnService(1)
                .name("Car Wash")
                .description("Full car wash service")
                .price(50.0)
                .isActive(true)
                .parkingLot(parkingLot)
                .build();

        addOnServiceDto = new AddOnServiceDto(
            1,
            "Car Wash",
            "Full car wash service",
            50.0,
            true,
            parkingLot
        );
    }

    @Test
    void getAllAddOnServices_ShouldReturnListOfAddOnServices() {
        // Arrange
        List<AddOnService> addOnServices = Arrays.asList(addOnServiceEntity);
        List<AddOnServiceDto> expectedDtos = Arrays.asList(addOnServiceDto);
        when(addOnServiceRepository.findAll()).thenReturn(addOnServices);
        when(addOnServiceMapper.mapFrom(addOnServices)).thenReturn(expectedDtos);

        // Act
        List<AddOnServiceDto> result = addOnService.getAllAddOnServices();

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idAddOnService(), result.get(0).idAddOnService());
        verify(addOnServiceRepository).findAll();
        verify(addOnServiceMapper).mapFrom(addOnServices);
    }

    @Test
    void getAddOnServiceById_WhenAddOnServiceExists_ShouldReturnAddOnService() {
        // Arrange
        when(addOnServiceRepository.findById(1)).thenReturn(Optional.of(addOnServiceEntity));
        when(addOnServiceMapper.mapFrom(addOnServiceEntity)).thenReturn(addOnServiceDto);

        // Act
        Optional<AddOnServiceDto> result = addOnService.getAddOnServiceById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(addOnServiceDto.idAddOnService(), result.get().idAddOnService());
        assertEquals(addOnServiceDto.name(), result.get().name());
        verify(addOnServiceRepository).findById(1);
        verify(addOnServiceMapper).mapFrom(addOnServiceEntity);
    }

    @Test
    void getAddOnServiceById_WhenAddOnServiceDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(addOnServiceRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<AddOnServiceDto> result = addOnService.getAddOnServiceById(999);

        // Assert
        assertTrue(result.isEmpty());
        verify(addOnServiceRepository).findById(999);
        verify(addOnServiceMapper, never()).mapFrom(any(AddOnService.class));
    }

    @Test
    void saveAddOnService_ShouldReturnSavedAddOnService() {
        // Arrange
        when(addOnServiceMapper.mapFrom(addOnServiceDto)).thenReturn(addOnServiceEntity);
        when(addOnServiceRepository.save(addOnServiceEntity)).thenReturn(addOnServiceEntity);
        when(addOnServiceMapper.mapFrom(addOnServiceEntity)).thenReturn(addOnServiceDto);

        // Act
        AddOnServiceDto result = addOnService.saveAddOnService(addOnServiceDto);

        // Assert
        assertNotNull(result);
        assertEquals(addOnServiceDto.idAddOnService(), result.idAddOnService());
        assertEquals(addOnServiceDto.name(), result.name());
        verify(addOnServiceMapper).mapFrom(addOnServiceDto);
        verify(addOnServiceRepository).save(addOnServiceEntity);
        verify(addOnServiceMapper).mapFrom(addOnServiceEntity);
    }

    @Test
    void deleteAddOnService_WhenAddOnServiceExists_ShouldMarkAsInactive() {
        // Arrange
        when(addOnServiceRepository.findById(1)).thenReturn(Optional.of(addOnServiceEntity));
        when(addOnServiceRepository.save(addOnServiceEntity)).thenReturn(addOnServiceEntity);

        // Act
        addOnService.deleteAddOnService(1);

        // Assert
        verify(addOnServiceRepository).findById(1);
        verify(addOnServiceRepository).save(addOnServiceEntity);
        assertFalse(addOnServiceEntity.getIsActive());
    }

    @Test
    void deleteAddOnService_WhenAddOnServiceDoesNotExist_ShouldThrowException() {
        // Arrange
        when(addOnServiceRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> addOnService.deleteAddOnService(999));
        verify(addOnServiceRepository).findById(999);
        verify(addOnServiceRepository, never()).save(any(AddOnService.class));
    }

    @Test
    void getAddOnServicesByIsActive_ShouldReturnFilteredAddOnServices() {
        // Arrange
        List<AddOnService> addOnServices = Arrays.asList(addOnServiceEntity);
        List<AddOnServiceDto> expectedDtos = Arrays.asList(addOnServiceDto);
        when(addOnServiceRepository.findByIsActive(true)).thenReturn(addOnServices);
        when(addOnServiceMapper.mapFrom(addOnServices)).thenReturn(expectedDtos);

        // Act
        List<AddOnServiceDto> result = addOnService.getAddOnServicesByIsActive(true);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idAddOnService(), result.get(0).idAddOnService());
        verify(addOnServiceRepository).findByIsActive(true);
        verify(addOnServiceMapper).mapFrom(addOnServices);
    }

    @Test
    void getAddOnServicesByParkingLotId_ShouldReturnFilteredAddOnServices() {
        // Arrange
        List<AddOnService> addOnServices = Arrays.asList(addOnServiceEntity);
        List<AddOnServiceDto> expectedDtos = Arrays.asList(addOnServiceDto);
        when(addOnServiceRepository.findByParkingLotAndIsActive(1)).thenReturn(addOnServices);
        when(addOnServiceMapper.mapFrom(addOnServices)).thenReturn(expectedDtos);

        // Act
        List<AddOnServiceDto> result = addOnService.getAddOnServicesByParkingLotId(1);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idAddOnService(), result.get(0).idAddOnService());
        verify(addOnServiceRepository).findByParkingLotAndIsActive(1);
        verify(addOnServiceMapper).mapFrom(addOnServices);
    }
}
