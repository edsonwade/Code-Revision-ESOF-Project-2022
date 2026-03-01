import { useMutation, useQueryClient } from '@tanstack/react-query';
import { queryKeys } from '@shared/constants/queryKeys';
import { createStudent } from '@entities/student/api/studentApi';
import type { CreateStudentRequest } from '@entities/student/model/student.types';

export function useCreateStudent() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (data: CreateStudentRequest) => createStudent(data),
    onSuccess: () => {
      void qc.invalidateQueries({ queryKey: queryKeys.students.all });
    },
  });
}
