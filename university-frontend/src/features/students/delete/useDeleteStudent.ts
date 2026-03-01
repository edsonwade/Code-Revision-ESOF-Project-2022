import { useMutation, useQueryClient } from '@tanstack/react-query';
import { queryKeys } from '@shared/constants/queryKeys';
import { deleteStudent } from '@entities/student/api/studentApi';

export function useDeleteStudent() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (id: number) => deleteStudent(id),
    onSuccess: () => {
      void qc.invalidateQueries({ queryKey: queryKeys.students.all });
    },
  });
}
