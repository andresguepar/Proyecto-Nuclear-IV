package pn.proyectonuclear4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.proyectonuclear4.entity.Payment;
import pn.proyectonuclear4.entity.PaymentMethod;
import pn.proyectonuclear4.entity.StandardReservation;
import pn.proyectonuclear4.entity.StatusReservation;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.StandardReservationDto;
import pn.proyectonuclear4.mapping.mappers.StandardReservationMapper;
import pn.proyectonuclear4.repository.*;
import pn.proyectonuclear4.service.StandardReservationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StandardReservationServiceImpl implements StandardReservationService {

    @Autowired
    private StandardReservationRepository standardReservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusReservationRepository statusReservationRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private EmailServiceImpl emailService;

    @Override
    public List<StandardReservationDto> getAllStandardReservations() {
        List<StandardReservation> standardReservations = standardReservationRepository.findAll();
        return StandardReservationMapper.mapFrom(standardReservations);
    }

    @Override
    public Optional<StandardReservationDto> getStandardReservationById(int id) {
        Optional<StandardReservation> standardReservation = standardReservationRepository.findById(id);
        return standardReservation.map(StandardReservationMapper::mapFrom);
    }

    @Override
    public StandardReservationDto saveStandardReservation(StandardReservationDto standardReservationDto) {
        StandardReservation standardReservation = StandardReservationMapper.mapFrom(standardReservationDto);
        StandardReservation savedStandardReservation = standardReservationRepository.save(standardReservation);
        return StandardReservationMapper.mapFrom(savedStandardReservation);
    }

    @Override
    public void deleteStandardReservation(int id) throws ResourceNotFoundException {
        StandardReservation standardReservation = standardReservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Standard reservation not found with id: " + id));

        StatusReservation canceledStatus = statusReservationRepository.findByName("CANCELED")
                .orElseThrow(() -> new ResourceNotFoundException("Status 'CANCELED' not found"));

        standardReservation.setStatusReservation(canceledStatus);
        standardReservationRepository.save(standardReservation);
    }

    @Override
    public List<StandardReservationDto> getStandardReservationsByUserId(int userId) {
        List<StandardReservation> standardReservations = standardReservationRepository.findByUser(userRepository.findById(userId).orElseThrow());
        return StandardReservationMapper.mapFrom(standardReservations);
    }

    @Override
    public List<StandardReservationDto> getStandardReservationsByParkingLotIdAndDateRange(int parkingLotId, LocalDateTime startDate, LocalDateTime endDate) {
        List<StandardReservation> standardReservations = standardReservationRepository.findByParkingLotAndDateRange(parkingLotId, startDate, endDate);
        return StandardReservationMapper.mapFrom(standardReservations);
    }

    @Override
    public StandardReservationDto updateStandardReservation(int id, StandardReservationDto standardReservationDto) throws ResourceNotFoundException {
        StandardReservation existingReservation = standardReservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Standard reservation not found with id: " + id));

        // Update only the allowed fields
        if (standardReservationDto.getScheduledDateTime() != null) {
            existingReservation.setScheduledDateTime(standardReservationDto.getScheduledDateTime());
        }
        if (standardReservationDto.getPlate() != null) {
            existingReservation.setPlate(standardReservationDto.getPlate());
        }
        if (standardReservationDto.getSlot() != null) {
            existingReservation.setSlot(standardReservationDto.getSlot());
        }

        StandardReservation updatedReservation = standardReservationRepository.save(existingReservation);
        return StandardReservationMapper.mapFrom(updatedReservation);
    }

    @Override
    public StandardReservationDto confirmReservation(int id) throws ResourceNotFoundException {
        StandardReservation reservation = standardReservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Standard reservation not found with id: " + id));

        // NO cambiar el estado - se mantiene en PENDIENTE (ID: 1)
        // Solo enviar correo al administrador del parqueadero
        String adminEmail = "admin@" + reservation.getSlot().getParkingLot().getName().toLowerCase().replace(" ", "") + ".com";
        String adminSubject = "Nueva Reserva Pendiente de Confirmación - Spot N Park";
        String adminBody = String.format(
            "Hola Administrador,\n\n" +
            "Se ha realizado una nueva reserva que requiere tu confirmación:\n\n" +
            "Detalles de la reserva:\n" +
            "- Usuario: %s\n" +
            "- Email: %s\n" +
            "- Fecha: %s\n" +
            "- Hora: %s\n" +
            "- Slot: %s\n" +
            "- Placa: %s\n\n" +
            "Por favor, verifica la disponibilidad y confirma la reserva desde el panel de administración.\n\n" +
            "Saludos,\nSpot N Park",
            reservation.getUser().getUsername(),
            reservation.getUser().getEmail(),
            reservation.getScheduledDateTime().toLocalDate(),
            reservation.getScheduledDateTime().toLocalTime(),
            reservation.getSlot().getName(),
            reservation.getPlate()
        );
        emailService.sendEmail(adminEmail, adminSubject, adminBody);

        // Enviar correo al usuario confirmando que su solicitud fue recibida
        String userEmail = reservation.getUser().getEmail();
        String userSubject = "Solicitud de Reserva Recibida - Spot N Park";
        String userBody = String.format(
            "Hola %s,\n\n" +
            "Tu solicitud de reserva en %s ha sido recibida y está siendo procesada.\n" +
            "Detalles de la reserva:\n" +
            "- Fecha: %s\n" +
            "- Hora: %s\n" +
            "- Slot: %s\n" +
            "- Placa: %s\n\n" +
            "El administrador del parqueadero revisará tu solicitud y te notificará cuando sea confirmada.\n\n" +
            "Gracias por usar Spot N Park!",
            reservation.getUser().getUsername(),
            reservation.getSlot().getParkingLot().getName(),
            reservation.getScheduledDateTime().toLocalDate(),
            reservation.getScheduledDateTime().toLocalTime(),
            reservation.getSlot().getName(),
            reservation.getPlate()
        );
        emailService.sendEmail(userEmail, userSubject, userBody);

        return StandardReservationMapper.mapFrom(reservation);
    }

    @Override
    public StandardReservationDto adminConfirmReservation(int id) throws ResourceNotFoundException {
        StandardReservation reservation = standardReservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Standard reservation not found with id: " + id));

        // Cambiar estado a CONFIRMADA (ID: 2)
        StatusReservation confirmedStatus = statusReservationRepository.findById(2)
                .orElseThrow(() -> new ResourceNotFoundException("Status 'CONFIRMADA' not found"));

        reservation.setStatusReservation(confirmedStatus);
        StandardReservation savedReservation = standardReservationRepository.save(reservation);

        // Enviar correo al usuario confirmando que la reserva fue aprobada
        String userEmail = reservation.getUser().getEmail();
        String subject = "Reserva Confirmada - Spot N Park";
        String body = String.format(
            "Hola %s,\n\n" +
            "¡Excelentes noticias! Tu reserva en %s ha sido confirmada por el administrador.\n" +
            "Detalles de la reserva:\n" +
            "- Fecha: %s\n" +
            "- Hora: %s\n" +
            "- Slot: %s\n" +
            "- Placa: %s\n\n" +
            "Tu reserva está lista. El administrador iniciará tu tiempo de estacionamiento cuando llegues.\n\n" +
            "¡Gracias por usar Spot N Park!",
            reservation.getUser().getUsername(),
            reservation.getSlot().getParkingLot().getName(),
            reservation.getScheduledDateTime().toLocalDate(),
            reservation.getScheduledDateTime().toLocalTime(),
            reservation.getSlot().getName(),
            reservation.getPlate()
        );
        emailService.sendEmail(userEmail, subject, body);

        return StandardReservationMapper.mapFrom(savedReservation);
    }

    @Override
    public StandardReservationDto startReservation(int id) throws ResourceNotFoundException {
        StandardReservation reservation = standardReservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Standard reservation not found with id: " + id));

        // Cambiar estado a EN CURSO (ID: 3)
        StatusReservation inProgressStatus = statusReservationRepository.findById(3)
                .orElseThrow(() -> new ResourceNotFoundException("Status 'EN CURSO' not found"));

        reservation.setStatusReservation(inProgressStatus);
        reservation.setCheckIn(LocalDateTime.now());
        StandardReservation savedReservation = standardReservationRepository.save(reservation);

        // Enviar correo al usuario
        String userEmail = reservation.getUser().getEmail();
        String subject = "Reserva Iniciada - Spot N Park";
        String body = String.format(
            "Hola %s,\n\n" +
            "Tu reserva en %s ha sido iniciada.\n" +
            "Detalles de la reserva:\n" +
            "- Fecha: %s\n" +
            "- Hora de inicio: %s\n" +
            "- Slot: %s\n" +
            "- Placa: %s\n\n" +
            "Tu tiempo de estacionamiento ha comenzado.\n\n" +
            "Gracias por usar Spot N Park!",
            reservation.getUser().getUsername(),
            reservation.getSlot().getParkingLot().getName(),
            reservation.getScheduledDateTime().toLocalDate(),
            LocalDateTime.now().toLocalTime(),
            reservation.getSlot().getName(),
            reservation.getPlate()
        );
        emailService.sendEmail(userEmail, subject, body);

        return StandardReservationMapper.mapFrom(savedReservation);
    }

    @Override
    public StandardReservationDto completeReservation(int id) throws ResourceNotFoundException {
        StandardReservation reservation = standardReservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Standard reservation not found with id: " + id));

        // Cambiar estado a COMPLETADA (ID: 4)
        StatusReservation completedStatus = statusReservationRepository.findById(4)
                .orElseThrow(() -> new ResourceNotFoundException("Status 'COMPLETADA' not found"));

        reservation.setStatusReservation(completedStatus);
        reservation.setCheckOut(LocalDateTime.now());
        StandardReservation savedReservation = standardReservationRepository.save(reservation);

        // Enviar correo al usuario
        String userEmail = reservation.getUser().getEmail();
        String subject = "Reserva Completada - Spot N Park";
        String body = String.format(
            "Hola %s,\n\n" +
            "Tu reserva en %s ha sido completada.\n" +
            "Detalles de la reserva:\n" +
            "- Fecha: %s\n" +
            "- Hora de salida: %s\n" +
            "- Slot: %s\n" +
            "- Placa: %s\n\n" +
            "Es hora de proceder con el pago.\n\n" +
            "Gracias por usar Spot N Park!",
            reservation.getUser().getUsername(),
            reservation.getSlot().getParkingLot().getName(),
            reservation.getScheduledDateTime().toLocalDate(),
            LocalDateTime.now().toLocalTime(),
            reservation.getSlot().getName(),
            reservation.getPlate()
        );
        emailService.sendEmail(userEmail, subject, body);

        return StandardReservationMapper.mapFrom(savedReservation);
    }

    @Override
    public StandardReservationDto processPayment(int id, int paymentMethodId) throws ResourceNotFoundException {
        StandardReservation reservation = standardReservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Standard reservation not found with id: " + id));

        // Verificar que la reserva esté completada
        if (reservation.getStatusReservation().getIdStatusReservation() != 4) {
            throw new ResourceNotFoundException("Reservation must be completed before processing payment");
        }

        // Calcular las horas de estacionamiento
        double hours = 0.0;
        if (reservation.getCheckIn() != null && reservation.getCheckOut() != null) {
            long minutes = java.time.Duration.between(reservation.getCheckIn(), reservation.getCheckOut()).toMinutes();
            hours = Math.ceil(minutes / 60.0); // Redondear hacia arriba
        }

        // Obtener el precio por hora del parqueadero
        double pricePerHour = 15.0; // Valor por defecto, se puede obtener de la entidad ParkingLot
        double totalAmount = hours * pricePerHour;

        // Crear el pago
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment method not found with id: " + paymentMethodId));

        Payment payment = Payment.builder()
                .paymentDate(LocalDateTime.now())
                .status("PAID")
                .paymentMethod(paymentMethod)
                .fee(null) // Se puede agregar la lógica para crear la fee si es necesario
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        // Asignar el pago a la reserva
        reservation.setPayment(savedPayment);
        StandardReservation savedReservation = standardReservationRepository.save(reservation);

        // Enviar correo de confirmación de pago
        String userEmail = reservation.getUser().getEmail();
        String subject = "Pago Procesado - Spot N Park";
        String body = String.format(
            "Hola %s,\n\n" +
            "Tu pago por la reserva en %s ha sido procesado exitosamente.\n" +
            "Detalles del pago:\n" +
            "- Horas de estacionamiento: %.1f horas\n" +
            "- Precio por hora: $%.2f\n" +
            "- Total: $%.2f\n" +
            "- Método de pago: %s\n" +
            "- Fecha: %s\n\n" +
            "¡Gracias por usar Spot N Park!",
            reservation.getUser().getUsername(),
            reservation.getSlot().getParkingLot().getName(),
            hours,
            pricePerHour,
            totalAmount,
            savedPayment.getPaymentMethod().getName(),
            savedPayment.getPaymentDate()
        );
        emailService.sendEmail(userEmail, subject, body);

        return StandardReservationMapper.mapFrom(savedReservation);
    }

    @Override
    public List<StandardReservationDto> getStandardReservationsByParkingLotAdmin(int adminId) {
        System.out.println("DEBUG: Buscando reservas para admin ID: " + adminId);

        // Verificar si el admin tiene parking lots
        int parkingLotsCount = standardReservationRepository.countParkingLotsByAdmin(adminId);
        System.out.println("DEBUG: Parking lots del admin: " + parkingLotsCount);

        // Usar la consulta original
        List<StandardReservation> standardReservations = standardReservationRepository.findByParkingLotAdmin(adminId);
        System.out.println("DEBUG: Reservas encontradas (consulta original): " + standardReservations.size());

        // Usar la consulta de debug
        List<StandardReservation> standardReservationsDebug = standardReservationRepository.findByParkingLotAdminDebug(adminId);
        System.out.println("DEBUG: Reservas encontradas (consulta debug): " + standardReservationsDebug.size());

        // Imprimir detalles de las reservas encontradas
        for (StandardReservation reservation : standardReservations) {
            System.out.println("DEBUG: Reserva ID: " + reservation.getIdStandardReservation() +
                             ", Usuario: " + reservation.getUser().getUsername() +
                             ", Parking Lot: " + reservation.getSlot().getParkingLot().getName() +
                             ", Admin ID: " + reservation.getSlot().getParkingLot().getAdmin().getIdUser());
        }

        // Usar la consulta que funcione mejor
        List<StandardReservation> finalReservations = standardReservationsDebug.size() > 0 ? standardReservationsDebug : standardReservations;

        return StandardReservationMapper.mapFrom(finalReservations);
    }

    @Override
    public StandardReservationDto userRequestStart(int id) throws ResourceNotFoundException {
        StandardReservation reservation = standardReservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Standard reservation not found with id: " + id));

        // Verificar que la reserva esté confirmada
        if (reservation.getStatusReservation().getIdStatusReservation() != 2) {
            throw new ResourceNotFoundException("Reservation must be confirmed before requesting start");
        }

        // Enviar correo al administrador solicitando iniciar la reserva
        String adminEmail = "admin@" + reservation.getSlot().getParkingLot().getName().toLowerCase().replace(" ", "") + ".com";
        String adminSubject = "Solicitud de Inicio de Reserva - Spot N Park";
        String adminBody = String.format(
            "Hola Administrador,\n\n" +
            "El usuario %s solicita iniciar su reserva:\n\n" +
            "Detalles de la reserva:\n" +
            "- Usuario: %s\n" +
            "- Email: %s\n" +
            "- Fecha: %s\n" +
            "- Hora: %s\n" +
            "- Slot: %s\n" +
            "- Placa: %s\n\n" +
            "Por favor, confirma el inicio de la reserva desde el panel de administración.\n\n" +
            "Saludos,\nSpot N Park",
            reservation.getUser().getUsername(),
            reservation.getUser().getUsername(),
            reservation.getUser().getEmail(),
            reservation.getScheduledDateTime().toLocalDate(),
            reservation.getScheduledDateTime().toLocalTime(),
            reservation.getSlot().getName(),
            reservation.getPlate()
        );
        emailService.sendEmail(adminEmail, adminSubject, adminBody);

        // Enviar correo al usuario confirmando que su solicitud fue enviada
        String userEmail = reservation.getUser().getEmail();
        String userSubject = "Solicitud de Inicio Enviada - Spot N Park";
        String userBody = String.format(
            "Hola %s,\n\n" +
            "Tu solicitud para iniciar la reserva en %s ha sido enviada al administrador.\n" +
            "Detalles de la reserva:\n" +
            "- Fecha: %s\n" +
            "- Hora: %s\n" +
            "- Slot: %s\n" +
            "- Placa: %s\n\n" +
            "El administrador confirmará el inicio de tu reserva.\n\n" +
            "Gracias por usar Spot N Park!",
            reservation.getUser().getUsername(),
            reservation.getSlot().getParkingLot().getName(),
            reservation.getScheduledDateTime().toLocalDate(),
            reservation.getScheduledDateTime().toLocalTime(),
            reservation.getSlot().getName(),
            reservation.getPlate()
        );
        emailService.sendEmail(userEmail, userSubject, userBody);

        return StandardReservationMapper.mapFrom(reservation);
    }

    @Override
    public int countParkingLotsByAdmin(int adminId) {
        return standardReservationRepository.countParkingLotsByAdmin(adminId);
    }
}
