import { useQuery } from '@tanstack/react-query';
import { queryKeys } from '@shared/constants/queryKeys';
import { getAvailabilities } from '../api/availabilityApi';
import type { AvailabilityDto } from '../model/availability.types';

export function useAvailability() {
  return useQuery<AvailabilityDto[]>({ queryKey: queryKeys.availability.all, queryFn: getAvailabilities, staleTime: 5 * 60 * 1000 });
}
