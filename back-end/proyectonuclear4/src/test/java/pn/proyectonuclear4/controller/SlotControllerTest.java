package pn.proyectonuclear4.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import pn.proyectonuclear4.entity.ParkingLot;
import pn.proyectonuclear4.entity.VehicleType;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.SlotDto;
import pn.proyectonuclear4.service.SlotService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SlotControllerTest {

    @Mock
    private SlotService slotService;

    @InjectMocks
    private SlotController slotController;

    private SlotDto slotDto;
    private ParkingLot parkingLot;
    private VehicleType vehicleType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        parkingLot = ParkingLot.builder()
                .idParkingLot(1)
                .name("Lot 1")
                .isActive(true)
                .build();
        vehicleType = VehicleType.builder()
                .idVehicleType(1)
                .name("Car")
                .isActive(true)
                .build();
        slotDto = new SlotDto(
                1,
                "A1",
                true,
                true,
                parkingLot,
                vehicleType
        );
    }

    @Test
    void getAllSlots_ShouldReturnListOfSlots() {
        List<SlotDto> expected = Arrays.asList(slotDto);
        when(slotService.getAllSlots()).thenReturn(expected);
        ResponseEntity<List<SlotDto>> response = slotController.getAllSlots();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
        verify(slotService, times(1)).getAllSlots();
    }

    @Test
    void getSlotById_WhenExists_ShouldReturnSlot() {
        when(slotService.getSlotById(1)).thenReturn(Optional.of(slotDto));
        ResponseEntity<SlotDto> response = slotController.getSlotById(1);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(slotDto, response.getBody());
        verify(slotService, times(1)).getSlotById(1);
    }

    @Test
    void getSlotById_WhenNotExists_ShouldReturnNotFound() {
        when(slotService.getSlotById(999)).thenReturn(Optional.empty());
        ResponseEntity<SlotDto> response = slotController.getSlotById(999);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(slotService, times(1)).getSlotById(999);
    }

    @Test
    void saveSlot_ShouldReturnSavedSlot() {
        when(slotService.saveSlot(any(SlotDto.class))).thenReturn(slotDto);
        ResponseEntity<SlotDto> response = slotController.saveSlot(slotDto);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(slotDto, response.getBody());
        verify(slotService, times(1)).saveSlot(slotDto);
    }

    @Test
    void deleteSlot_WhenExists_ShouldReturnSuccess() {
        doNothing().when(slotService).deleteSlot(1);
        ResponseEntity<String> response = slotController.deleteSlot(1);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Slot status updated to inactive.", response.getBody());
        verify(slotService, times(1)).deleteSlot(1);
    }

    @Test
    void deleteSlot_WhenNotExists_ShouldReturnNotFound() {
        doThrow(new ResourceNotFoundException("Slot not found")).when(slotService).deleteSlot(999);
        ResponseEntity<String> response = slotController.deleteSlot(999);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(slotService, times(1)).deleteSlot(999);
    }

    @Test
    void getSlotsByIsActive_ShouldReturnFilteredSlots() {
        List<SlotDto> expected = Arrays.asList(slotDto);
        when(slotService.getSlotsByIsActive(true)).thenReturn(expected);
        ResponseEntity<List<SlotDto>> response = slotController.getSlotsByIsActive(true);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
        verify(slotService, times(1)).getSlotsByIsActive(true);
    }

    @Test
    void getSlotsByParkingLotIdAndVehicleTypeIdAndByIsAvailable_ShouldReturnFilteredSlots() {
        List<SlotDto> expected = Arrays.asList(slotDto);
        when(slotService.getSlotsByParkingLotIdAndVehicleTypeIdAndByIsAvailable(1, 1, true)).thenReturn(expected);
        ResponseEntity<List<SlotDto>> response = slotController.getSlotsByParkingLotIdAndVehicleTypeIdAndByIsAvailable(1, 1, true);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
        verify(slotService, times(1)).getSlotsByParkingLotIdAndVehicleTypeIdAndByIsAvailable(1, 1, true);
    }
} 