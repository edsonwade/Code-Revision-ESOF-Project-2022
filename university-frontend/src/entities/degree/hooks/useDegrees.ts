import { useQuery } from '@tanstack/react-query';
import { queryKeys } from '@shared/constants/queryKeys';
import { getDegrees } from '../api/degreeApi';
export function useDegrees() {
  return useQuery({ queryKey: queryKeys.degrees.all, queryFn: getDegrees, staleTime: 10 * 60 * 1000, refetchOnWindowFocus: false });
}
