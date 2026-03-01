import { useMutation, useQueryClient } from '@tanstack/react-query';
import { queryKeys } from '@shared/constants/queryKeys';
import { deleteAppointment } from '@entities/appointment/api/appointmentApi';
export function useDeleteAppointment() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (id: number) => deleteAppointment(id),
    onSuccess: () => { void qc.invalidateQueries({ queryKey: queryKeys.appointments.all }); },
  });
}
