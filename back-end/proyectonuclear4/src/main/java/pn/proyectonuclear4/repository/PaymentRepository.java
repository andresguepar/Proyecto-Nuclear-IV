package pn.proyectonuclear4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pn.proyectonuclear4.entity.Payment;
import pn.proyectonuclear4.entity.PaymentMethod;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("""
        SELECT p FROM Payment p 
        WHERE p IN (
            SELECT sr.payment FROM StandardReservation sr WHERE sr.user.id = :userId AND sr.payment IS NOT NULL
        )
        OR p IN (
            SELECT mr.payment FROM MonthlyReservation mr WHERE mr.user.id = :userId AND mr.payment IS NOT NULL
        )
    """)
    List<Payment> findPaymentsByUserId(@Param("userId") int userId);
    @Query("""
        SELECT p FROM Payment p 
        WHERE p IN (
            SELECT sr.payment FROM StandardReservation sr WHERE sr.slot.parkingLot.idParkingLot = :parkingLotId AND sr.payment IS NOT NULL
        )
        OR p IN (
            SELECT mr.payment FROM MonthlyReservation mr WHERE mr.slot.parkingLot.idParkingLot = :parkingLotId AND mr.payment IS NOT NULL
        )
    """)
    List<Payment> findPaymentsByParkingLotId(@Param("parkingLotId") int parkingLotId);
    List<Payment> findPaymentsByPaymentMethod(PaymentMethod paymentMethod);
}
