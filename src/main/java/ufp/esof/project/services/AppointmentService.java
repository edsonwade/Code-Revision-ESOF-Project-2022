package ufp.esof.project.services;

import ufp.esof.project.dto.request.CreateAppointmentRequest;
import ufp.esof.project.dto.response.AppointmentResponse;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {

    List<AppointmentResponse> getAllAppointments();

    Optional<AppointmentResponse> getAppointmentById(Long id);

    Optional<AppointmentResponse> createAppointment(CreateAppointmentRequest request);

    boolean deleteAppointment(Long id);
}
