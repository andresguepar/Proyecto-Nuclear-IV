package pn.proyectonuclear4.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pn.proyectonuclear4.mapping.dto.SlotDto;
import pn.proyectonuclear4.entity.Slot;
import pn.proyectonuclear4.entity.ParkingLot;
import pn.proyectonuclear4.entity.VehicleType;
import pn.proyectonuclear4.mapping.mappers.SlotMapper;
import pn.proyectonuclear4.repository.SlotRepository;
import pn.proyectonuclear4.service.impl.SlotServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SlotServiceTest {

    @Mock
    private SlotRepository slotRepository;

    @Mock
    private SlotMapper slotMapper;

    @InjectMocks
    private SlotServiceImpl slotService;

    private Slot slot;
    private SlotDto slotDto;
    private ParkingLot parkingLot;
    private VehicleType vehicleType;

    @BeforeEach
    void setUp() {
        parkingLot = ParkingLot.builder()
                .idParkingLot(1)
                .address("Test Address")
                .latitude(10.0)
                .longitude(20.0)
                .contactPhone("123456789")
                .contactEmail("test@test.com")
                .isActive(true)
                .build();

        vehicleType = VehicleType.builder()
                .idVehicleType(1)
                .name("Car")
                .isActive(true)
                .build();

        slot = Slot.builder()
                .idSlot(1)
                .slotName("A1")
                .isAvailable(true)
                .isActive(true)
                .parkingLot(parkingLot)
                .vehicleType(vehicleType)
                .build();

        slotDto = new SlotDto(
            1,
            "A1",
            true,
            true,
            1,
            1
        );
    }

    @Test
    void getAllSlots_ShouldReturnListOfSlots() {
        // Arrange
        List<Slot> slots = Arrays.asList(slot);
        List<SlotDto> expectedDtos = Arrays.asList(slotDto);
        when(slotRepository.findAll()).thenReturn(slots);
        when(slotMapper.mapFrom(any(Slot.class))).thenReturn(slotDto);

        // Act
        List<SlotDto> result = slotService.getAllSlots();

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idSlot(), result.get(0).idSlot());
        verify(slotRepository).findAll();
        verify(slotMapper).mapFrom(any(Slot.class));
    }

    @Test
    void getSlotById_WhenSlotExists_ShouldReturnSlot() {
        // Arrange
        when(slotRepository.findById(1)).thenReturn(Optional.of(slot));
        when(slotMapper.mapFrom(slot)).thenReturn(slotDto);

        // Act
        Optional<SlotDto> result = slotService.getSlotById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(slotDto.idSlot(), result.get().idSlot());
        assertEquals(slotDto.slotName(), result.get().slotName());
        verify(slotRepository).findById(1);
        verify(slotMapper).mapFrom(slot);
    }

    @Test
    void getSlotById_WhenSlotDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(slotRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<SlotDto> result = slotService.getSlotById(999);

        // Assert
        assertTrue(result.isEmpty());
        verify(slotRepository).findById(999);
        verify(slotMapper, never()).mapFrom(any(Slot.class));
    }

    @Test
    void saveSlot_ShouldReturnSavedSlot() {
        // Arrange
        when(slotMapper.mapFrom(any(SlotDto.class))).thenReturn(slot);
        when(slotRepository.save(any(Slot.class))).thenReturn(slot);
        when(slotMapper.mapFrom(any(Slot.class))).thenReturn(slotDto);

        // Act
        SlotDto result = slotService.saveSlot(slotDto);

        // Assert
        assertNotNull(result);
        assertEquals(slotDto.idSlot(), result.idSlot());
        assertEquals(slotDto.slotName(), result.slotName());
        verify(slotMapper).mapFrom(slotDto);
        verify(slotRepository).save(any(Slot.class));
        verify(slotMapper).mapFrom(slot);
    }

    @Test
    void deleteSlot_WhenSlotExists_ShouldMarkAsInactive() {
        // Arrange
        when(slotRepository.findById(1)).thenReturn(Optional.of(slot));
        when(slotRepository.save(any(Slot.class))).thenReturn(slot);

        // Act
        slotService.deleteSlot(1);

        // Assert
        verify(slotRepository).findById(1);
        verify(slotRepository).save(any(Slot.class));
        assertFalse(slot.getIsActive());
    }

    @Test
    void deleteSlot_WhenSlotDoesNotExist_ShouldThrowException() {
        // Arrange
        when(slotRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> slotService.deleteSlot(999));
        verify(slotRepository).findById(999);
        verify(slotRepository, never()).save(any(Slot.class));
    }

    @Test
    void getSlotsByIsActive_ShouldReturnFilteredSlots() {
        // Arrange
        List<Slot> slots = Arrays.asList(slot);
        List<SlotDto> expectedDtos = Arrays.asList(slotDto);
        when(slotRepository.findByIsActive(true)).thenReturn(slots);
        when(slotMapper.mapFrom(any(Slot.class))).thenReturn(slotDto);

        // Act
        List<SlotDto> result = slotService.getSlotsByIsActive(true);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idSlot(), result.get(0).idSlot());
        verify(slotRepository).findByIsActive(true);
        verify(slotMapper).mapFrom(any(Slot.class));
    }

    @Test
    void getSlotsByParkingLotIdAndVehicleTypeIdAndByIsAvailable_ShouldReturnFilteredSlots() {
        // Arrange
        List<Slot> slots = Arrays.asList(slot);
        List<SlotDto> expectedDtos = Arrays.asList(slotDto);
        when(slotRepository.findByParkingLotIdParkingLotAndVehicleTypeIdVehicleTypeAndIsAvailable(1, 1, true))
            .thenReturn(slots);
        when(slotMapper.mapFrom(any(Slot.class))).thenReturn(slotDto);

        // Act
        List<SlotDto> result = slotService.getSlotsByParkingLotIdAndVehicleTypeIdAndByIsAvailable(1, 1, true);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idSlot(), result.get(0).idSlot());
        verify(slotRepository).findByParkingLotIdParkingLotAndVehicleTypeIdVehicleTypeAndIsAvailable(1, 1, true);
        verify(slotMapper).mapFrom(any(Slot.class));
    }
} 