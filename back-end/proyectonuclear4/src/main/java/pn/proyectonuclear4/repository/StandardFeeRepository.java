package pn.proyectonuclear4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pn.proyectonuclear4.entity.StandardFee;

import java.util.List;

@Repository
public interface StandardFeeRepository extends JpaRepository<StandardFee, Integer> {
    List<StandardFee> findByIsActive(Boolean isActive);
}
