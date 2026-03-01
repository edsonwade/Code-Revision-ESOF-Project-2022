package ufp.esof.project.mapper;

import org.springframework.stereotype.Component;
import ufp.esof.project.dto.request.CreateAppointmentRequest;
import ufp.esof.project.dto.response.AppointmentResponse;
import ufp.esof.project.models.Appointment;

import static ufp.esof.project.services.AppointmentServiceImpl.getAppointmentResponse;

/**
 * Mapper for converting between Appointment entities and DTOs.
 */
@Component
public class AppointmentMapper {

    /**
     * Convert CreateAppointmentRequest DTO to Appointment entity.
     *
     * @param request the request DTO
     * @return the appointment entity
     */
    public Appointment toEntity(CreateAppointmentRequest request) {
        if (request == null) {
            return null;
        }

        return new Appointment(request.getStartTime(), request.getEndTime());
    }

    /**
     * Convert Appointment entity to AppointmentResponse DTO.
     *
     * @param appointment the appointment entity
     * @return the response DTO
     */
    public AppointmentResponse toResponse(Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        return getAppointmentResponse(appointment);
    }
}

