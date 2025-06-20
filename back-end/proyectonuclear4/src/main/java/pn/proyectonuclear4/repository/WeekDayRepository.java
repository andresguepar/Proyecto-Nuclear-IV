package pn.proyectonuclear4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pn.proyectonuclear4.entity.WeekDay;

@Repository
public interface WeekDayRepository extends JpaRepository<WeekDay, Integer> {
}
