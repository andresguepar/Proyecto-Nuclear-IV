package pn.proyectonuclear4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.proyectonuclear4.entity.PaymentMethod;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.PaymentMethodDto;
import pn.proyectonuclear4.mapping.mappers.PaymentMethodMapper;
import pn.proyectonuclear4.repository.PaymentMethodRepository;
import pn.proyectonuclear4.service.PaymentMethodService;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethodDto> getAllPaymentMethods() {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findAll();
        return PaymentMethodMapper.mapFrom(paymentMethods);
    }

    @Override
    public Optional<PaymentMethodDto> getPaymentMethodById(int id) {
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(id);
        return paymentMethod.map(PaymentMethodMapper::mapFrom);
    }

    @Override
    public PaymentMethodDto savePaymentMethod(PaymentMethodDto paymentMethodDto) {
        PaymentMethod paymentMethod = PaymentMethodMapper.mapFrom(paymentMethodDto);
        PaymentMethod savedPaymentMethod = paymentMethodRepository.save(paymentMethod);
        return PaymentMethodMapper.mapFrom(savedPaymentMethod);
    }

    @Override
    public void deletePaymentMethod(int id) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("PaymentMethod not found with id " + id));
        paymentMethod.setIsActive(false);
        paymentMethodRepository.save(paymentMethod);
    }

    @Override
    public List<PaymentMethodDto> getPaymentMethodsByIsActive(Boolean isActive) {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findByIsActive(isActive);
        return PaymentMethodMapper.mapFrom(paymentMethods);
    }
}
