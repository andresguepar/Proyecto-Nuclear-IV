package pn.proyectonuclear4.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pn.proyectonuclear4.mapping.dto.WeekDayDto;
import pn.proyectonuclear4.entity.WeekDay;
import pn.proyectonuclear4.mapping.mappers.WeekDayMapper;
import pn.proyectonuclear4.repository.WeekDayRepository;
import pn.proyectonuclear4.service.impl.WeekDayServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeekDayServiceTest {

    @Mock
    private WeekDayRepository weekDayRepository;

    @Mock
    private WeekDayMapper weekDayMapper;

    @InjectMocks
    private WeekDayServiceImpl weekDayService;

    private WeekDay weekDay;
    private WeekDayDto weekDayDto;

    @BeforeEach
    void setUp() {
        weekDay = WeekDay.builder()
                .idWeekDay(1)
                .name("Monday")
                .build();

        weekDayDto = new WeekDayDto(
            1,
            "Monday"
        );
    }

    @Test
    void getAllWeekDays_ShouldReturnListOfWeekDays() {
        // Arrange
        List<WeekDay> weekDays = Arrays.asList(weekDay);
        List<WeekDayDto> expectedDtos = Arrays.asList(weekDayDto);
        when(weekDayRepository.findAll()).thenReturn(weekDays);
        when(weekDayMapper.mapFrom(any(WeekDay.class))).thenReturn(weekDayDto);

        // Act
        List<WeekDayDto> result = weekDayService.getAllWeekDays();

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idWeekDay(), result.get(0).idWeekDay());
        verify(weekDayRepository).findAll();
        verify(weekDayMapper).mapFrom(any(WeekDay.class));
    }

    @Test
    void getWeekDayById_WhenWeekDayExists_ShouldReturnWeekDay() {
        // Arrange
        when(weekDayRepository.findById(1)).thenReturn(Optional.of(weekDay));
        when(weekDayMapper.mapFrom(weekDay)).thenReturn(weekDayDto);

        // Act
        Optional<WeekDayDto> result = weekDayService.getWeekDayById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(weekDayDto.idWeekDay(), result.get().idWeekDay());
        assertEquals(weekDayDto.name(), result.get().name());
        verify(weekDayRepository).findById(1);
        verify(weekDayMapper).mapFrom(weekDay);
    }

    @Test
    void getWeekDayById_WhenWeekDayDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(weekDayRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<WeekDayDto> result = weekDayService.getWeekDayById(999);

        // Assert
        assertTrue(result.isEmpty());
        verify(weekDayRepository).findById(999);
        verify(weekDayMapper, never()).mapFrom(any(WeekDay.class));
    }

    @Test
    void saveWeekDay_ShouldReturnSavedWeekDay() {
        // Arrange
        when(weekDayMapper.mapFrom(any(WeekDayDto.class))).thenReturn(weekDay);
        when(weekDayRepository.save(any(WeekDay.class))).thenReturn(weekDay);
        when(weekDayMapper.mapFrom(any(WeekDay.class))).thenReturn(weekDayDto);

        // Act
        WeekDayDto result = weekDayService.saveWeekDay(weekDayDto);

        // Assert
        assertNotNull(result);
        assertEquals(weekDayDto.idWeekDay(), result.idWeekDay());
        assertEquals(weekDayDto.name(), result.name());
        verify(weekDayMapper).mapFrom(weekDayDto);
        verify(weekDayRepository).save(any(WeekDay.class));
        verify(weekDayMapper).mapFrom(weekDay);
    }

    @Test
    void deleteWeekDay_WhenWeekDayExists_ShouldMarkAsInactive() {
        // Arrange
        when(weekDayRepository.findById(1)).thenReturn(Optional.of(weekDay));
        when(weekDayRepository.save(any(WeekDay.class))).thenReturn(weekDay);

        // Act
        weekDayService.deleteWeekDay(1);

        // Assert
        verify(weekDayRepository).findById(1);
        verify(weekDayRepository).save(any(WeekDay.class));
        assertFalse(weekDay.getIsActive());
    }

    @Test
    void deleteWeekDay_WhenWeekDayDoesNotExist_ShouldThrowException() {
        // Arrange
        when(weekDayRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> weekDayService.deleteWeekDay(999));
        verify(weekDayRepository).findById(999);
        verify(weekDayRepository, never()).save(any(WeekDay.class));
    }

    @Test
    void getWeekDaysByIsActive_ShouldReturnFilteredWeekDays() {
        // Arrange
        List<WeekDay> weekDays = Arrays.asList(weekDay);
        List<WeekDayDto> expectedDtos = Arrays.asList(weekDayDto);
        when(weekDayRepository.findByIsActive(true)).thenReturn(weekDays);
        when(weekDayMapper.mapFrom(any(WeekDay.class))).thenReturn(weekDayDto);

        // Act
        List<WeekDayDto> result = weekDayService.getWeekDaysByIsActive(true);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idWeekDay(), result.get(0).idWeekDay());
        verify(weekDayRepository).findByIsActive(true);
        verify(weekDayMapper).mapFrom(any(WeekDay.class));
    }
} 