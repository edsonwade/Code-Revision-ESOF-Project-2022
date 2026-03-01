import { useQuery } from '@tanstack/react-query';
import { queryKeys } from '@shared/constants/queryKeys';
import { getExplainers } from '../api/explainerApi';

export function useExplainers() {
  return useQuery({
    queryKey: queryKeys.explainers.all,
    queryFn: getExplainers,
    staleTime: 5 * 60 * 1000,
  });
}
