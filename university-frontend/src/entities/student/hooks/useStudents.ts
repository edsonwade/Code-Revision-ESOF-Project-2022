import { useQuery } from '@tanstack/react-query';
import { queryKeys } from '@shared/constants/queryKeys';
import { getStudents, searchStudentsByName } from '../api/studentApi';

export function useStudents() {
  return useQuery({
    queryKey: queryKeys.students.all,
    queryFn: getStudents,
    staleTime: 5 * 60 * 1000,
  });
}

export function useStudentSearch(name: string, enabled = true) {
  return useQuery({
    queryKey: queryKeys.students.byName(name),
    queryFn: () => searchStudentsByName(name),
    enabled: enabled && name.length > 0,
    staleTime: 2 * 60 * 1000,
  });
}
