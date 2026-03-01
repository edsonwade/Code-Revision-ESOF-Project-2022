import { httpClient } from '../../../shared/api/httpClient';
import { Appointment, CreateAppointmentRequest } from '../model/appointment.types';

export const appointmentApi = {
  getAppointments: (): Promise<Appointment[]> => httpClient.get('/appointments'),
  getAppointmentById: (id: number): Promise<Appointment> => httpClient.get(`/appointments/${id}`),
  createAppointment: (data: CreateAppointmentRequest): Promise<Appointment> => httpClient.post('/appointments', data),
  deleteAppointment: (id: number): Promise<string> => httpClient.delete(`/appointments/${id}`),
};
