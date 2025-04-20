package pn.proyectonuclear4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pn.proyectonuclear4.entity.AddOnServiceFee;

import java.util.List;

@Repository
public interface AddOnServiceFeeRepository extends JpaRepository<AddOnServiceFee, Integer> {
    List<AddOnServiceFee> findByIsActive(Boolean isActive);
}
