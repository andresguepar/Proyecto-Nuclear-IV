package pn.proyectonuclear4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "daily_schedules")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailySchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDailySchedule;

    @ManyToOne
    @JoinColumn(name = "id_schedule")
    private Schedule schedule;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "id_week_day")
    private WeekDay weekDay;

    private Boolean isActive;
} 