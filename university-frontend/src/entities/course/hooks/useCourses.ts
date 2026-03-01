import { useQuery } from '@tanstack/react-query';
import { queryKeys } from '@shared/constants/queryKeys';
import { getCourses } from '../api/courseApi';
export function useCourses() {
  return useQuery({ queryKey: queryKeys.courses.all, queryFn: getCourses, staleTime: 10 * 60 * 1000, refetchOnWindowFocus: false });
}
