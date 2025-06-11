package pn.proyectonuclear4.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pn.proyectonuclear4.mapping.dto.AddOnDto;
import pn.proyectonuclear4.entity.AddOn;
import pn.proyectonuclear4.mapping.mappers.AddOnMapper;
import pn.proyectonuclear4.repository.AddOnRepository;
import pn.proyectonuclear4.service.impl.AddOnServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddOnServiceTest {

    @Mock
    private AddOnRepository addOnRepository;

    @Mock
    private AddOnMapper addOnMapper;

    @InjectMocks
    private AddOnServiceImpl addOnService;

    private AddOn addOn;
    private AddOnDto addOnDto;

    @BeforeEach
    void setUp() {
        addOn = AddOn.builder()
                .idAddOn(1)
                .name("Car Wash")
                .description("Full car wash service")
                .isActive(true)
                .build();

        addOnDto = new AddOnDto(
            1,
            "Car Wash",
            "Full car wash service",
            true
        );
    }

    @Test
    void getAllAddOns_ShouldReturnListOfAddOns() {
        // Arrange
        List<AddOn> addOns = Arrays.asList(addOn);
        List<AddOnDto> expectedDtos = Arrays.asList(addOnDto);
        when(addOnRepository.findAll()).thenReturn(addOns);
        when(addOnMapper.mapFrom(any(AddOn.class))).thenReturn(addOnDto);

        // Act
        List<AddOnDto> result = addOnService.getAllAddOns();

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idAddOn(), result.get(0).idAddOn());
        verify(addOnRepository).findAll();
        verify(addOnMapper).mapFrom(any(AddOn.class));
    }

    @Test
    void getAddOnById_WhenAddOnExists_ShouldReturnAddOn() {
        // Arrange
        when(addOnRepository.findById(1)).thenReturn(Optional.of(addOn));
        when(addOnMapper.mapFrom(addOn)).thenReturn(addOnDto);

        // Act
        Optional<AddOnDto> result = addOnService.getAddOnById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(addOnDto.idAddOn(), result.get().idAddOn());
        assertEquals(addOnDto.name(), result.get().name());
        verify(addOnRepository).findById(1);
        verify(addOnMapper).mapFrom(addOn);
    }

    @Test
    void getAddOnById_WhenAddOnDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(addOnRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<AddOnDto> result = addOnService.getAddOnById(999);

        // Assert
        assertTrue(result.isEmpty());
        verify(addOnRepository).findById(999);
        verify(addOnMapper, never()).mapFrom(any(AddOn.class));
    }

    @Test
    void saveAddOn_ShouldReturnSavedAddOn() {
        // Arrange
        when(addOnMapper.mapFrom(any(AddOnDto.class))).thenReturn(addOn);
        when(addOnRepository.save(any(AddOn.class))).thenReturn(addOn);
        when(addOnMapper.mapFrom(any(AddOn.class))).thenReturn(addOnDto);

        // Act
        AddOnDto result = addOnService.saveAddOn(addOnDto);

        // Assert
        assertNotNull(result);
        assertEquals(addOnDto.idAddOn(), result.idAddOn());
        assertEquals(addOnDto.name(), result.name());
        verify(addOnMapper).mapFrom(addOnDto);
        verify(addOnRepository).save(any(AddOn.class));
        verify(addOnMapper).mapFrom(addOn);
    }

    @Test
    void deleteAddOn_WhenAddOnExists_ShouldMarkAsInactive() {
        // Arrange
        when(addOnRepository.findById(1)).thenReturn(Optional.of(addOn));
        when(addOnRepository.save(any(AddOn.class))).thenReturn(addOn);

        // Act
        addOnService.deleteAddOn(1);

        // Assert
        verify(addOnRepository).findById(1);
        verify(addOnRepository).save(any(AddOn.class));
        assertFalse(addOn.getIsActive());
    }

    @Test
    void deleteAddOn_WhenAddOnDoesNotExist_ShouldThrowException() {
        // Arrange
        when(addOnRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> addOnService.deleteAddOn(999));
        verify(addOnRepository).findById(999);
        verify(addOnRepository, never()).save(any(AddOn.class));
    }

    @Test
    void getAddOnsByIsActive_ShouldReturnFilteredAddOns() {
        // Arrange
        List<AddOn> addOns = Arrays.asList(addOn);
        List<AddOnDto> expectedDtos = Arrays.asList(addOnDto);
        when(addOnRepository.findByIsActive(true)).thenReturn(addOns);
        when(addOnMapper.mapFrom(any(AddOn.class))).thenReturn(addOnDto);

        // Act
        List<AddOnDto> result = addOnService.getAddOnsByIsActive(true);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idAddOn(), result.get(0).idAddOn());
        verify(addOnRepository).findByIsActive(true);
        verify(addOnMapper).mapFrom(any(AddOn.class));
    }
} 