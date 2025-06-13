package pn.proyectonuclear4.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pn.proyectonuclear4.entity.StandardReservation;
import pn.proyectonuclear4.entity.StatusReservation;
import pn.proyectonuclear4.entity.User;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.StandardReservationDto;
import pn.proyectonuclear4.mapping.mappers.StandardReservationMapper;
import pn.proyectonuclear4.service.StandardReservationService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StandardReservationController.class)
class StandardReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StandardReservationService standardReservationService;

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
    void getAllStandardReservations_ShouldReturnListOfReservations() throws Exception {
        List<StandardReservationDto> reservations = Arrays.asList(standardReservationDto);
        when(standardReservationService.getAllStandardReservations()).thenReturn(reservations);

        mockMvc.perform(get("/standard-reservations/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idStandardReservation", is(standardReservationDto.idStandardReservation())))
                .andExpect(jsonPath("$[0].plate", is(standardReservationDto.plate())));

        verify(standardReservationService).getAllStandardReservations();
    }

    @Test
    void getStandardReservationById_WhenExists_ShouldReturnReservation() throws Exception {
        when(standardReservationService.getStandardReservationById(1)).thenReturn(Optional.of(standardReservationDto));

        mockMvc.perform(get("/standard-reservations/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idStandardReservation", is(standardReservationDto.idStandardReservation())))
                .andExpect(jsonPath("$.plate", is(standardReservationDto.plate())));

        verify(standardReservationService).getStandardReservationById(1);
    }

    @Test
    void getStandardReservationById_WhenNotExists_ShouldReturnNotFound() throws Exception {
        when(standardReservationService.getStandardReservationById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/standard-reservations/get/999"))
                .andExpect(status().isNotFound());

        verify(standardReservationService).getStandardReservationById(999);
    }

    @Test
    void saveStandardReservation_ShouldReturnSavedReservation() throws Exception {
        when(standardReservationService.saveStandardReservation(any(StandardReservationDto.class)))
                .thenReturn(standardReservationDto);

        mockMvc.perform(post("/standard-reservations/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(standardReservationDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idStandardReservation", is(standardReservationDto.idStandardReservation())))
                .andExpect(jsonPath("$.plate", is(standardReservationDto.plate())));

        verify(standardReservationService).saveStandardReservation(any(StandardReservationDto.class));
    }

    @Test
    void deleteStandardReservation_WhenExists_ShouldReturnSuccessMessage() throws Exception {
        doNothing().when(standardReservationService).deleteStandardReservation(1);

        mockMvc.perform(delete("/standard-reservations/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Standard reservation status updated to canceled."));

        verify(standardReservationService).deleteStandardReservation(1);
    }

    @Test
    void deleteStandardReservation_WhenNotExists_ShouldReturnNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Standard reservation not found"))
                .when(standardReservationService).deleteStandardReservation(999);

        mockMvc.perform(delete("/standard-reservations/delete/999"))
                .andExpect(status().isNotFound());

        verify(standardReservationService).deleteStandardReservation(999);
    }

    @Test
    void updateStandardReservation_WhenExists_ShouldReturnUpdatedReservation() throws Exception {
        when(standardReservationService.updateStandardReservation(anyInt(), any(StandardReservationDto.class)))
                .thenReturn(standardReservationDto);

        mockMvc.perform(put("/standard-reservations/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(standardReservationDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idStandardReservation", is(standardReservationDto.idStandardReservation())))
                .andExpect(jsonPath("$.plate", is(standardReservationDto.plate())));

        verify(standardReservationService).updateStandardReservation(anyInt(), any(StandardReservationDto.class));
    }

    @Test
    void updateStandardReservation_WhenNotExists_ShouldReturnNotFound() throws Exception {
        when(standardReservationService.updateStandardReservation(anyInt(), any(StandardReservationDto.class)))
                .thenThrow(new ResourceNotFoundException("Standard reservation not found"));

        mockMvc.perform(put("/standard-reservations/update/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(standardReservationDto)))
                .andExpect(status().isNotFound());

        verify(standardReservationService).updateStandardReservation(anyInt(), any(StandardReservationDto.class));
    }

    @Test
    void getStandardReservationsByUserId_ShouldReturnListOfReservations() throws Exception {
        List<StandardReservationDto> reservations = Arrays.asList(standardReservationDto);
        when(standardReservationService.getStandardReservationsByUserId(1)).thenReturn(reservations);

        mockMvc.perform(get("/standard-reservations/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idStandardReservation", is(standardReservationDto.idStandardReservation())))
                .andExpect(jsonPath("$[0].plate", is(standardReservationDto.plate())));

        verify(standardReservationService).getStandardReservationsByUserId(1);
    }

    @Test
    void getStandardReservationsByParkingLotIdAndDateRange_ShouldReturnListOfReservations() throws Exception {
        List<StandardReservationDto> reservations = Arrays.asList(standardReservationDto);
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusDays(1);

        when(standardReservationService.getStandardReservationsByParkingLotIdAndDateRange(anyInt(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(reservations);

        mockMvc.perform(get("/standard-reservations/parking-lot/1/date-range")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idStandardReservation", is(standardReservationDto.idStandardReservation())))
                .andExpect(jsonPath("$[0].plate", is(standardReservationDto.plate())));

        verify(standardReservationService).getStandardReservationsByParkingLotIdAndDateRange(anyInt(), any(LocalDateTime.class), any(LocalDateTime.class));
    }
} 