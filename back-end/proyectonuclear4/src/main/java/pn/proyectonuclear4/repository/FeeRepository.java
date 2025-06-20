package pn.proyectonuclear4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pn.proyectonuclear4.entity.Fee;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Integer> {
}
