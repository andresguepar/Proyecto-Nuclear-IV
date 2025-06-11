package pn.proyectonuclear4.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pn.proyectonuclear4.mapping.dto.DailyScheduleDto;
import pn.proyectonuclear4.entity.DailySchedule;
import pn.proyectonuclear4.entity.Schedule;
import pn.proyectonuclear4.entity.WeekDay;
import pn.proyectonuclear4.mapping.mappers.DailyScheduleMapper;
import pn.proyectonuclear4.repository.DailyScheduleRepository;
import pn.proyectonuclear4.service.impl.DailyScheduleServiceImpl;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DailyScheduleServiceTest {

    @Mock
    private DailyScheduleRepository dailyScheduleRepository;

    @Mock
    private DailyScheduleMapper dailyScheduleMapper;

    @InjectMocks
    private DailyScheduleServiceImpl dailyScheduleService;

    private DailySchedule dailySchedule;
    private DailyScheduleDto dailyScheduleDto;
    private Schedule schedule;
    private WeekDay weekDay;

    @BeforeEach
    void setUp() {
        schedule = Schedule.builder()
                .idSchedule(1)
                .isActive(true)
                .build();

        weekDay = WeekDay.builder()
                .idWeekDay(1)
                .name("Monday")
                .build();

        dailySchedule = DailySchedule.builder()
                .idDailySchedule(1)
                .schedule(schedule)
                .startTime(LocalTime.of(8, 0))
                .endTime(LocalTime.of(18, 0))
                .weekDay(weekDay)
                .isActive(true)
                .build();

        dailyScheduleDto = new DailyScheduleDto(
            1,
            schedule,
            LocalTime.of(8, 0),
            LocalTime.of(18, 0),
            weekDay,
            true
        );
    }

    @Test
    void getAllDailySchedules_ShouldReturnListOfDailySchedules() {
        // Arrange
        List<DailySchedule> dailySchedules = Arrays.asList(dailySchedule);
        List<DailyScheduleDto> expectedDtos = Arrays.asList(dailyScheduleDto);
        when(dailyScheduleRepository.findAll()).thenReturn(dailySchedules);
        when(dailyScheduleMapper.mapFrom(any(DailySchedule.class))).thenReturn(dailyScheduleDto);

        // Act
        List<DailyScheduleDto> result = dailyScheduleService.getAllDailySchedules();

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idDailySchedule(), result.get(0).idDailySchedule());
        verify(dailyScheduleRepository).findAll();
        verify(dailyScheduleMapper).mapFrom(any(DailySchedule.class));
    }

    @Test
    void getDailyScheduleById_WhenDailyScheduleExists_ShouldReturnDailySchedule() {
        // Arrange
        when(dailyScheduleRepository.findById(1)).thenReturn(Optional.of(dailySchedule));
        when(dailyScheduleMapper.mapFrom(dailySchedule)).thenReturn(dailyScheduleDto);

        // Act
        Optional<DailyScheduleDto> result = dailyScheduleService.getDailyScheduleById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(dailyScheduleDto.idDailySchedule(), result.get().idDailySchedule());
        assertEquals(dailyScheduleDto.startTime(), result.get().startTime());
        verify(dailyScheduleRepository).findById(1);
        verify(dailyScheduleMapper).mapFrom(dailySchedule);
    }

    @Test
    void getDailyScheduleById_WhenDailyScheduleDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(dailyScheduleRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<DailyScheduleDto> result = dailyScheduleService.getDailyScheduleById(999);

        // Assert
        assertTrue(result.isEmpty());
        verify(dailyScheduleRepository).findById(999);
        verify(dailyScheduleMapper, never()).mapFrom(any(DailySchedule.class));
    }

    @Test
    void saveDailySchedule_ShouldReturnSavedDailySchedule() {
        // Arrange
        when(dailyScheduleMapper.mapFrom(any(DailyScheduleDto.class))).thenReturn(dailySchedule);
        when(dailyScheduleRepository.save(any(DailySchedule.class))).thenReturn(dailySchedule);
        when(dailyScheduleMapper.mapFrom(any(DailySchedule.class))).thenReturn(dailyScheduleDto);

        // Act
        DailyScheduleDto result = dailyScheduleService.saveDailySchedule(dailyScheduleDto);

        // Assert
        assertNotNull(result);
        assertEquals(dailyScheduleDto.idDailySchedule(), result.idDailySchedule());
        assertEquals(dailyScheduleDto.startTime(), result.startTime());
        verify(dailyScheduleMapper).mapFrom(dailyScheduleDto);
        verify(dailyScheduleRepository).save(any(DailySchedule.class));
        verify(dailyScheduleMapper).mapFrom(dailySchedule);
    }

    @Test
    void deleteDailySchedule_WhenDailyScheduleExists_ShouldMarkAsInactive() {
        // Arrange
        when(dailyScheduleRepository.findById(1)).thenReturn(Optional.of(dailySchedule));
        when(dailyScheduleRepository.save(any(DailySchedule.class))).thenReturn(dailySchedule);

        // Act
        dailyScheduleService.deleteDailySchedule(1);

        // Assert
        verify(dailyScheduleRepository).findById(1);
        verify(dailyScheduleRepository).save(any(DailySchedule.class));
        assertFalse(dailySchedule.getIsActive());
    }

    @Test
    void deleteDailySchedule_WhenDailyScheduleDoesNotExist_ShouldThrowException() {
        // Arrange
        when(dailyScheduleRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> dailyScheduleService.deleteDailySchedule(999));
        verify(dailyScheduleRepository).findById(999);
        verify(dailyScheduleRepository, never()).save(any(DailySchedule.class));
    }

    @Test
    void getDailySchedulesByIsActive_ShouldReturnFilteredDailySchedules() {
        // Arrange
        List<DailySchedule> dailySchedules = Arrays.asList(dailySchedule);
        List<DailyScheduleDto> expectedDtos = Arrays.asList(dailyScheduleDto);
        when(dailyScheduleRepository.findByIsActive(true)).thenReturn(dailySchedules);
        when(dailyScheduleMapper.mapFrom(any(DailySchedule.class))).thenReturn(dailyScheduleDto);

        // Act
        List<DailyScheduleDto> result = dailyScheduleService.getDailySchedulesByIsActive(true);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idDailySchedule(), result.get(0).idDailySchedule());
        verify(dailyScheduleRepository).findByIsActive(true);
        verify(dailyScheduleMapper).mapFrom(any(DailySchedule.class));
    }

    @Test
    void getDailySchedulesByWeekDayAndScheduleAndIsActive_ShouldReturnFilteredDailySchedules() {
        // Arrange
        List<DailySchedule> dailySchedules = Arrays.asList(dailySchedule);
        List<DailyScheduleDto> expectedDtos = Arrays.asList(dailyScheduleDto);
        when(dailyScheduleRepository.findByWeekDayAndScheduleAndIsActive(1, 1)).thenReturn(dailySchedules);
        when(dailyScheduleMapper.mapFrom(any(DailySchedule.class))).thenReturn(dailyScheduleDto);

        // Act
        List<DailyScheduleDto> result = dailyScheduleService.getDailySchedulesByWeekDayAndScheduleAndIsActive(1, 1, true);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idDailySchedule(), result.get(0).idDailySchedule());
        verify(dailyScheduleRepository).findByWeekDayAndScheduleAndIsActive(1, 1);
        verify(dailyScheduleMapper).mapFrom(any(DailySchedule.class));
    }
} 