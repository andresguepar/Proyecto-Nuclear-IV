package pn.proyectonuclear4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "week_days")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeekDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idWeekDay;

    private String name;
} 