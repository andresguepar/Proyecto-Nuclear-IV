package pn.proyectonuclear4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pn.proyectonuclear4.entity.MonthlyFee;

import java.util.List;

@Repository
public interface MonthlyFeeRepository extends JpaRepository<MonthlyFee, Integer> {
    List<MonthlyFee> findByIsActive(Boolean isActive);
}
