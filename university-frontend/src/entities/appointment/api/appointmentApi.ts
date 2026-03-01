import httpClient from '@shared/api/httpClient';
import { buildUrl } from '@shared/api/apiConfig';
import type { AppointmentDto, CreateAppointmentRequest } from '../model/appointment.types';

export async function getAppointments(): Promise<AppointmentDto[]> {
  const { data } = await httpClient.get<AppointmentDto[]>(buildUrl('/appointment'));
  return data;
}

export async function getAppointmentById(id: number): Promise<AppointmentDto> {
  const { data } = await httpClient.get<AppointmentDto>(buildUrl(`/appointment/${id}`));
  return data;
}

export async function createAppointment(body: CreateAppointmentRequest): Promise<AppointmentDto> {
  const { data } = await httpClient.post<AppointmentDto>(buildUrl('/appointment'), body);
  return data;
}

export async function deleteAppointment(id: number): Promise<string> {
  const { data } = await httpClient.delete<string>(buildUrl(`/appointment/delete/${id}`));
  return data;
}
