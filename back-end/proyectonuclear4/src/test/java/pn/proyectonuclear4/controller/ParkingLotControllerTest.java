package pn.proyectonuclear4.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pn.proyectonuclear4.entity.ParkingLot;
import pn.proyectonuclear4.entity.User;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.ParkingLotDto;
import pn.proyectonuclear4.mapping.mappers.ParkingLotMapper;
import pn.proyectonuclear4.service.ParkingLotService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ParkingLotController.class)
class ParkingLotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ParkingLotService parkingLotService;

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
    void getAll_ShouldReturnListOfParkingLots() throws Exception {
        List<ParkingLotDto> parkingLots = Arrays.asList(parkingLotDto);
        when(parkingLotService.getAllParkingLots()).thenReturn(parkingLots);

        mockMvc.perform(get("/ParkingLots/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idParkingLot", is(parkingLotDto.idParkingLot())))
                .andExpect(jsonPath("$[0].name", is(parkingLotDto.name())))
                .andExpect(jsonPath("$[0].address", is(parkingLotDto.address())));

        verify(parkingLotService).getAllParkingLots();
    }

    @Test
    void getById_WhenExists_ShouldReturnParkingLot() throws Exception {
        when(parkingLotService.getParkingLotById(1)).thenReturn(Optional.of(parkingLotDto));

        mockMvc.perform(get("/ParkingLots/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idParkingLot", is(parkingLotDto.idParkingLot())))
                .andExpect(jsonPath("$.name", is(parkingLotDto.name())))
                .andExpect(jsonPath("$.address", is(parkingLotDto.address())));

        verify(parkingLotService).getParkingLotById(1);
    }

    @Test
    void getById_WhenNotExists_ShouldReturnNotFound() throws Exception {
        when(parkingLotService.getParkingLotById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/ParkingLots/get/999"))
                .andExpect(status().isNotFound());

        verify(parkingLotService).getParkingLotById(999);
    }

    @Test
    void save_ShouldReturnSavedParkingLot() throws Exception {
        when(parkingLotService.saveParkingLot(any(ParkingLotDto.class))).thenReturn(parkingLotDto);

        mockMvc.perform(post("/ParkingLots/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parkingLotDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idParkingLot", is(parkingLotDto.idParkingLot())))
                .andExpect(jsonPath("$.name", is(parkingLotDto.name())))
                .andExpect(jsonPath("$.address", is(parkingLotDto.address())));

        verify(parkingLotService).saveParkingLot(any(ParkingLotDto.class));
    }

    @Test
    void delete_WhenExists_ShouldReturnSuccessMessage() throws Exception {
        doNothing().when(parkingLotService).deleteParkingLot(1);

        mockMvc.perform(delete("/ParkingLots/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("ParkingLot status changed to inactive successfully."));

        verify(parkingLotService).deleteParkingLot(1);
    }

    @Test
    void delete_WhenNotExists_ShouldReturnNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Parking lot not found"))
                .when(parkingLotService).deleteParkingLot(999);

        mockMvc.perform(delete("/ParkingLots/delete/999"))
                .andExpect(status().isNotFound());

        verify(parkingLotService).deleteParkingLot(999);
    }

    @Test
    void getByStatus_ShouldReturnFilteredParkingLots() throws Exception {
        List<ParkingLotDto> parkingLots = Arrays.asList(parkingLotDto);
        when(parkingLotService.getParkingLotsByIsActive(true)).thenReturn(parkingLots);

        mockMvc.perform(get("/ParkingLots/filterByStatus")
                .param("isActive", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idParkingLot", is(parkingLotDto.idParkingLot())))
                .andExpect(jsonPath("$[0].name", is(parkingLotDto.name())))
                .andExpect(jsonPath("$[0].address", is(parkingLotDto.address())));

        verify(parkingLotService).getParkingLotsByIsActive(true);
    }

    @Test
    void getByAdminAndStatus_ShouldReturnFilteredParkingLots() throws Exception {
        List<ParkingLotDto> parkingLots = Arrays.asList(parkingLotDto);
        when(parkingLotService.getParkingLotsByAdminAndIsActive(1, true)).thenReturn(parkingLots);

        mockMvc.perform(get("/ParkingLots/filterByAdmin")
                .param("adminId", "1")
                .param("isActive", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idParkingLot", is(parkingLotDto.idParkingLot())))
                .andExpect(jsonPath("$[0].name", is(parkingLotDto.name())))
                .andExpect(jsonPath("$[0].address", is(parkingLotDto.address())));

        verify(parkingLotService).getParkingLotsByAdminAndIsActive(1, true);
    }

    @Test
    void getByAdminAndStatus_WhenAdminNotExists_ShouldReturnNotFound() throws Exception {
        when(parkingLotService.getParkingLotsByAdminAndIsActive(999, true))
                .thenThrow(new ResourceNotFoundException("User not found"));

        mockMvc.perform(get("/ParkingLots/filterByAdmin")
                .param("adminId", "999")
                .param("isActive", "true"))
                .andExpect(status().isNotFound());

        verify(parkingLotService).getParkingLotsByAdminAndIsActive(999, true);
    }
}
