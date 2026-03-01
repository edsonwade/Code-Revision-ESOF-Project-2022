import { useQuery } from '@tanstack/react-query';
import { appointmentApi } from '../api/appointmentApi';
import { queryKeys } from '../../../shared/constants/queryKeys';

export const useAppointments = () => {
  return useQuery({
    queryKey: queryKeys.appointments.all,
    queryFn: appointmentApi.getAppointments,
  });
};

export const useAppointment = (id: number) => {
  return useQuery({
    queryKey: queryKeys.appointments.detail(id),
    queryFn: () => appointmentApi.getAppointmentById(id),
    enabled: !!id,
  });
};
