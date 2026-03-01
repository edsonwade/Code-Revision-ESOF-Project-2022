import { useMutation, useQueryClient } from '@tanstack/react-query';
import { queryKeys } from '@shared/constants/queryKeys';
import { createAppointment } from '@entities/appointment/api/appointmentApi';
import type { CreateAppointmentRequest } from '@entities/appointment/model/appointment.types';
export function useCreateAppointment() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (data: CreateAppointmentRequest) => createAppointment(data),
    onSuccess: () => { void qc.invalidateQueries({ queryKey: queryKeys.appointments.all }); },
  });
}
