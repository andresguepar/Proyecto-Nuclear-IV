package pn.proyectonuclear4.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pn.proyectonuclear4.mapping.dto.ScheduleDto;
import pn.proyectonuclear4.entity.Schedule;
import pn.proyectonuclear4.entity.ParkingLot;
import pn.proyectonuclear4.mapping.mappers.ScheduleMapper;
import pn.proyectonuclear4.repository.ScheduleRepository;
import pn.proyectonuclear4.service.impl.ScheduleServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ScheduleMapper scheduleMapper;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    private Schedule schedule;
    private ScheduleDto scheduleDto;
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

        schedule = Schedule.builder()
                .idSchedule(1)
                .parkingLot(parkingLot)
                .isActive(true)
                .build();

        scheduleDto = new ScheduleDto(1, parkingLot, true);
    }

    @Test
    void getAllSchedules_ShouldReturnListOfSchedules() {
        // Arrange
        List<Schedule> schedules = Arrays.asList(schedule);
        List<ScheduleDto> expectedDtos = Arrays.asList(scheduleDto);
        when(scheduleRepository.findAll()).thenReturn(schedules);
        when(scheduleMapper.mapFrom(any(Schedule.class))).thenReturn(scheduleDto);

        // Act
        List<ScheduleDto> result = scheduleService.getAllSchedules();

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idSchedule(), result.get(0).idSchedule());
        verify(scheduleRepository).findAll();
        verify(scheduleMapper).mapFrom(any(Schedule.class));
    }

    @Test
    void getScheduleById_WhenScheduleExists_ShouldReturnSchedule() {
        // Arrange
        when(scheduleRepository.findById(1)).thenReturn(Optional.of(schedule));
        when(scheduleMapper.mapFrom(schedule)).thenReturn(scheduleDto);

        // Act
        Optional<ScheduleDto> result = scheduleService.getScheduleById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(scheduleDto.idSchedule(), result.get().idSchedule());
        assertEquals(scheduleDto.isActive(), result.get().isActive());
        verify(scheduleRepository).findById(1);
        verify(scheduleMapper).mapFrom(schedule);
    }

    @Test
    void getScheduleById_WhenScheduleDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(scheduleRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<ScheduleDto> result = scheduleService.getScheduleById(999);

        // Assert
        assertTrue(result.isEmpty());
        verify(scheduleRepository).findById(999);
        verify(scheduleMapper, never()).mapFrom(any(Schedule.class));
    }

    @Test
    void saveSchedule_ShouldReturnSavedSchedule() {
        // Arrange
        when(scheduleMapper.mapFrom(any(ScheduleDto.class))).thenReturn(schedule);
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);
        when(scheduleMapper.mapFrom(any(Schedule.class))).thenReturn(scheduleDto);

        // Act
        ScheduleDto result = scheduleService.saveSchedule(scheduleDto);

        // Assert
        assertNotNull(result);
        assertEquals(scheduleDto.idSchedule(), result.idSchedule());
        assertEquals(scheduleDto.isActive(), result.isActive());
        verify(scheduleMapper).mapFrom(scheduleDto);
        verify(scheduleRepository).save(any(Schedule.class));
        verify(scheduleMapper).mapFrom(schedule);
    }

    @Test
    void deleteSchedule_WhenScheduleExists_ShouldMarkAsInactive() {
        // Arrange
        when(scheduleRepository.findById(1)).thenReturn(Optional.of(schedule));
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);

        // Act
        scheduleService.deleteSchedule(1);

        // Assert
        verify(scheduleRepository).findById(1);
        verify(scheduleRepository).save(any(Schedule.class));
        assertFalse(schedule.getIsActive());
    }

    @Test
    void deleteSchedule_WhenScheduleDoesNotExist_ShouldThrowException() {
        // Arrange
        when(scheduleRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> scheduleService.deleteSchedule(999));
        verify(scheduleRepository).findById(999);
        verify(scheduleRepository, never()).save(any(Schedule.class));
    }

    @Test
    void getSchedulesByIsActive_ShouldReturnFilteredSchedules() {
        // Arrange
        List<Schedule> schedules = Arrays.asList(schedule);
        List<ScheduleDto> expectedDtos = Arrays.asList(scheduleDto);
        when(scheduleRepository.findByIsActive(true)).thenReturn(schedules);
        when(scheduleMapper.mapFrom(any(Schedule.class))).thenReturn(scheduleDto);

        // Act
        List<ScheduleDto> result = scheduleService.getSchedulesByIsActive(true);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idSchedule(), result.get(0).idSchedule());
        verify(scheduleRepository).findByIsActive(true);
        verify(scheduleMapper).mapFrom(any(Schedule.class));
    }
} 