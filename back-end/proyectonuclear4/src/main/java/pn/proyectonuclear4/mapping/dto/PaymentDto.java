package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import lombok.Data;
import pn.proyectonuclear4.entity.Fee;
import pn.proyectonuclear4.entity.PaymentMethod;

import java.time.LocalDateTime;

@Data
@Builder
public class PaymentDto{
    private int idPayment;
    private LocalDateTime paymentDate;
    private String status;
    private PaymentMethod paymentMethod;
    private Fee fee;

}

