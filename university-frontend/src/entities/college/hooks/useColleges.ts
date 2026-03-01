import { useQuery } from '@tanstack/react-query';
import { queryKeys } from '@shared/constants/queryKeys';
import { getColleges } from '../api/collegeApi';

export function useColleges() {
  return useQuery({
    queryKey: queryKeys.colleges.all,
    queryFn: getColleges,
    staleTime: 10 * 60 * 1000,
    refetchOnWindowFocus: false,
  });
}
