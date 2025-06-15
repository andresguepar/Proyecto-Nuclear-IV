package pn.proyectonuclear4.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pn.proyectonuclear4.entity.VehicleType;
import pn.proyectonuclear4.mapping.dto.VehicleTypeDto;
import pn.proyectonuclear4.mapping.mappers.VehicleTypeMapper;
import pn.proyectonuclear4.repository.VehicleTypeRepository;
import pn.proyectonuclear4.service.impl.VehicleTypeServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleTypeServiceTest {

    @Mock
    private VehicleTypeRepository vehicleTypeRepository;

    @Mock
    private VehicleTypeMapper vehicleTypeMapper;

    @InjectMocks
    private VehicleTypeServiceImpl vehicleTypeService;

    private VehicleType vehicleType;
    private VehicleTypeDto vehicleTypeDto;

    @BeforeEach
    void setUp() {
        vehicleType = VehicleType.builder()
                .idVehicleType(1)
                .name("Car")
                .isActive(true)
                .build();

        vehicleTypeDto = new VehicleTypeDto(
            1,
            "Car",
            true
        );
    }

    @Test
    void getAllVehicleTypes_ShouldReturnListOfVehicleTypes() {
        // Arrange
        List<VehicleType> vehicleTypes = Arrays.asList(vehicleType);
        List<VehicleTypeDto> expectedDtos = Arrays.asList(vehicleTypeDto);
        when(vehicleTypeRepository.findAll()).thenReturn(vehicleTypes);
        when(vehicleTypeMapper.mapFrom(any(VehicleType.class))).thenReturn(vehicleTypeDto);

        // Act
        List<VehicleTypeDto> result = vehicleTypeService.getAllVehicleTypes();

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idVehicleType(), result.get(0).idVehicleType());
        verify(vehicleTypeRepository).findAll();
        verify(vehicleTypeMapper).mapFrom(any(VehicleType.class));
    }

    @Test
    void getVehicleTypeById_WhenVehicleTypeExists_ShouldReturnVehicleType() {
        // Arrange
        when(vehicleTypeRepository.findById(1)).thenReturn(Optional.of(vehicleType));
        when(vehicleTypeMapper.mapFrom(vehicleType)).thenReturn(vehicleTypeDto);

        // Act
        Optional<VehicleTypeDto> result = vehicleTypeService.getVehicleTypeById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(vehicleTypeDto.idVehicleType(), result.get().idVehicleType());
        assertEquals(vehicleTypeDto.name(), result.get().name());
        verify(vehicleTypeRepository).findById(1);
        verify(vehicleTypeMapper).mapFrom(vehicleType);
    }

    @Test
    void getVehicleTypeById_WhenVehicleTypeDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(vehicleTypeRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<VehicleTypeDto> result = vehicleTypeService.getVehicleTypeById(999);

        // Assert
        assertTrue(result.isEmpty());
        verify(vehicleTypeRepository).findById(999);
        verify(vehicleTypeMapper, never()).mapFrom(any(VehicleType.class));
    }

    @Test
    void saveVehicleType_ShouldReturnSavedVehicleType() {
        // Arrange
        when(vehicleTypeMapper.mapFrom(any(VehicleTypeDto.class))).thenReturn(vehicleType);
        when(vehicleTypeRepository.save(any(VehicleType.class))).thenReturn(vehicleType);
        when(vehicleTypeMapper.mapFrom(any(VehicleType.class))).thenReturn(vehicleTypeDto);

        // Act
        VehicleTypeDto result = vehicleTypeService.saveVehicleType(vehicleTypeDto);

        // Assert
        assertNotNull(result);
        assertEquals(vehicleTypeDto.idVehicleType(), result.idVehicleType());
        assertEquals(vehicleTypeDto.name(), result.name());
        verify(vehicleTypeMapper).mapFrom(vehicleTypeDto);
        verify(vehicleTypeRepository).save(any(VehicleType.class));
        verify(vehicleTypeMapper).mapFrom(vehicleType);
    }

    @Test
    void deleteVehicleType_WhenVehicleTypeExists_ShouldMarkAsInactive() {
        // Arrange
        when(vehicleTypeRepository.findById(1)).thenReturn(Optional.of(vehicleType));
        when(vehicleTypeRepository.save(any(VehicleType.class))).thenReturn(vehicleType);

        // Act
        vehicleTypeService.deleteVehicleType(1);

        // Assert
        verify(vehicleTypeRepository).findById(1);
        verify(vehicleTypeRepository).save(any(VehicleType.class));
        assertFalse(vehicleType.getIsActive());
    }

    @Test
    void deleteVehicleType_WhenVehicleTypeDoesNotExist_ShouldThrowException() {
        // Arrange
        when(vehicleTypeRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> vehicleTypeService.deleteVehicleType(999));
        verify(vehicleTypeRepository).findById(999);
        verify(vehicleTypeRepository, never()).save(any(VehicleType.class));
    }

    @Test
    void getVehicleTypesByIsActive_ShouldReturnFilteredVehicleTypes() {
        // Arrange
        List<VehicleType> vehicleTypes = Arrays.asList(vehicleType);
        List<VehicleTypeDto> expectedDtos = Arrays.asList(vehicleTypeDto);
        when(vehicleTypeRepository.findByIsActive(true)).thenReturn(vehicleTypes);
        when(vehicleTypeMapper.mapFrom(any(VehicleType.class))).thenReturn(vehicleTypeDto);

        // Act
        List<VehicleTypeDto> result = vehicleTypeService.getVehicleTypesByIsActive(true);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idVehicleType(), result.get(0).idVehicleType());
        verify(vehicleTypeRepository).findByIsActive(true);
        verify(vehicleTypeMapper).mapFrom(any(VehicleType.class));
    }
}
