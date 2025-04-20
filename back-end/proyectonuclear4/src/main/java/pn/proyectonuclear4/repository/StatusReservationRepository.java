package pn.proyectonuclear4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pn.proyectonuclear4.entity.StatusReservation;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatusReservationRepository extends JpaRepository<StatusReservation, Integer> {
    List<StatusReservation> findByIsActive(Boolean isActive);
}
