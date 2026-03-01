import { useQuery } from '@tanstack/react-query';
import { studentApi } from '../api/studentApi';
import { queryKeys } from '../../../shared/constants/queryKeys';

export const useStudents = () => {
  return useQuery({
    queryKey: queryKeys.students.all,
    queryFn: studentApi.getStudents,
  });
};

export const useStudent = (id: number) => {
  return useQuery({
    queryKey: queryKeys.students.detail(id),
    queryFn: () => studentApi.getStudentById(id),
    enabled: !!id,
  });
};
