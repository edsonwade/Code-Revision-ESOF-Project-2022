import { useMutation, useQueryClient } from '@tanstack/react-query';
import { queryKeys } from '@shared/constants/queryKeys';
import { deleteExplainer } from '@entities/explainer/api/explainerApi';
export function useDeleteExplainer() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (id: number) => deleteExplainer(id),
    onSuccess: () => { void qc.invalidateQueries({ queryKey: queryKeys.explainers.all }); },
  });
}
