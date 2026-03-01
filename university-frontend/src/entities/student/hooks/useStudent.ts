import { useQuery } from '@tanstack/react-query';
import { queryKeys } from '@shared/constants/queryKeys';
import { getStudentById } from '../api/studentApi';

export function useStudent(id: number) {
  return useQuery({
    queryKey: queryKeys.students.detail(id),
    queryFn: () => getStudentById(id),
    enabled: id > 0,
    staleTime: 5 * 60 * 1000,
  });
}
