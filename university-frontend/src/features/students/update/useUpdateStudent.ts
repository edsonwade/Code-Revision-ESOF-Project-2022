import { useMutation, useQueryClient } from '@tanstack/react-query';
import { queryKeys } from '@shared/constants/queryKeys';
import { updateStudent } from '@entities/student/api/studentApi';
import type { CreateStudentRequest } from '@entities/student/model/student.types';

export function useUpdateStudent(id: number) {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (data: CreateStudentRequest) => updateStudent(id, data),
    onSuccess: () => {
      void qc.invalidateQueries({ queryKey: queryKeys.students.all });
      void qc.invalidateQueries({ queryKey: queryKeys.students.detail(id) });
    },
  });
}
