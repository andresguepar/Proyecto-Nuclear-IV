package pn.proyectonuclear4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pn.proyectonuclear4.entity.VehicleType;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Integer> {
    List<VehicleType> findByIsActive(Boolean isActive);
}
