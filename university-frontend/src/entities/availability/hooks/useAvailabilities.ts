import { useQuery } from '@tanstack/react-query';
import { queryKeys } from '@shared/constants/queryKeys';
import { getAvailabilities, getAvailabilityById } from '../api/availabilityApi';

export function useAvailabilities() {
  return useQuery({
    queryKey: queryKeys.availability.all,
    queryFn: getAvailabilities,
    staleTime: 5 * 60 * 1000,
  });
}

export function useAvailability(id: number) {
  return useQuery({
    queryKey: queryKeys.availability.detail(id),
    queryFn: () => getAvailabilityById(id),
    staleTime: 5 * 60 * 1000,
    enabled: id > 0,
  });
}
