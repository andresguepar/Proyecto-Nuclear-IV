package pn.proyectonuclear4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pn.proyectonuclear4.entity.AddOnService;

import java.util.List;

@Repository
public interface AddOnServiceRepository extends JpaRepository<AddOnService, Integer> {
    List<AddOnService> findByIsActive(Boolean isActive);
    @Query("SELECT aos FROM AddOnService aos WHERE aos.parkingLot.idParkingLot = :parkingLotId AND aos.isActive = true")
    List<AddOnService> findByParkingLotAndIsActive(@Param("parkingLotId")int parkingLotId);

}
