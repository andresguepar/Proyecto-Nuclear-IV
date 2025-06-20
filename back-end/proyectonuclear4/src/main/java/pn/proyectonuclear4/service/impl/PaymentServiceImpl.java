package pn.proyectonuclear4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.proyectonuclear4.entity.Payment;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.PaymentDto;
import pn.proyectonuclear4.mapping.mappers.PaymentMapper;
import pn.proyectonuclear4.repository.PaymentMethodRepository;
import pn.proyectonuclear4.repository.PaymentRepository;
import pn.proyectonuclear4.service.PaymentService;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;


    @Override
    public List<PaymentDto> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return PaymentMapper.mapFrom(payments);
    }

    @Override
    public Optional<PaymentDto> getPaymentById(int id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        return payment.map(PaymentMapper::mapFrom);
    }

    @Override
    public PaymentDto savePayment(PaymentDto paymentDto) {
        Payment payment = PaymentMapper.mapFrom(paymentDto);
        Payment savedPayment = paymentRepository.save(payment);
        return PaymentMapper.mapFrom(savedPayment);
    }

    @Override
    public void deletePayment(int id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        payment.setStatus("deleted");
        paymentRepository.save(payment);
    }

    @Override
    public List<PaymentDto> getPaymentsByPaymentMethod(int paymentMethodId) {
        List<Payment> payments = paymentRepository.findPaymentsByPaymentMethod(paymentMethodRepository.findById(paymentMethodId).orElseThrow(() -> new ResourceNotFoundException("Payment not found")));
        return PaymentMapper.mapFrom(payments);
    }


    @Override
    public List<PaymentDto> getPaymentsByParkingLotId(int parkingLotId) {
        List<Payment> payments = paymentRepository.findPaymentsByParkingLotId(parkingLotId);
        return PaymentMapper.mapFrom(payments);
    }

    @Override
    public List<PaymentDto> getPaymentsByUserId(int userId) {
        List<Payment> payments = paymentRepository.findPaymentsByUserId(userId);
        return PaymentMapper.mapFrom(payments);
    }
}
