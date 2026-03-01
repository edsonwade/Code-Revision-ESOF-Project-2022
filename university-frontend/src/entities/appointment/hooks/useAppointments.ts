import { useQuery } from '@tanstack/react-query';
import { queryKeys } from '@shared/constants/queryKeys';
import { getAppointments } from '../api/appointmentApi';

export function useAppointments() {
  return useQuery({
    queryKey: queryKeys.appointments.all,
    queryFn: getAppointments,
    staleTime: 5 * 60 * 1000,
  });
}
