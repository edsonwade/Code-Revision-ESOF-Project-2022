import { useQuery } from '@tanstack/react-query';
import { queryKeys } from '@shared/constants/queryKeys';
import { getAvailability } from '../api/availabilityApi';
export function useAvailability() {
  return useQuery({ queryKey: queryKeys.availability.all, queryFn: getAvailability, staleTime: 5 * 60 * 1000 });
}
