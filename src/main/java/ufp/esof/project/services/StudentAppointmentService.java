/**
 * Author: vanilson muhongo
 * Date:16/06/2025
 * Time:14:11
 * Version:1
 */

package ufp.esof.project.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;
import ufp.esof.project.exception.AppointmentNotFoundException;
import ufp.esof.project.models.Appointment;
import ufp.esof.project.models.Student;
import ufp.esof.project.repositories.AppointmentRepository;
@Component
public class StudentAppointmentService {

    private final AppointmentRepository appointmentRepository;

    public StudentAppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    // Versão corrigida do método:
    public Set<Appointment> validateAndAttachAppointments(Student student, Set<Appointment> appointments) {
        Set<Appointment> validAppointments = new HashSet<>();

        for (Appointment appointment : appointments) {
            Appointment foundAppointment = appointmentRepository.findById(appointment.getId())
                    .orElseThrow(() -> new AppointmentNotFoundException("Appointment with ID " + appointment.getId() + " not found"));

            foundAppointment.setStudent(student);
            validAppointments.add(foundAppointment);
        }

        return validAppointments;
    }

}
