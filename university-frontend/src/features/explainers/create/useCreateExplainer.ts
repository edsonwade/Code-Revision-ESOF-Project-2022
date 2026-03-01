import { useMutation, useQueryClient } from '@tanstack/react-query';
import { queryKeys } from '@shared/constants/queryKeys';
import { createExplainer } from '@entities/explainer/api/explainerApi';
import type { CreateExplainerRequest } from '@entities/explainer/model/explainer.types';
export function useCreateExplainer() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (data: CreateExplainerRequest) => createExplainer(data),
    onSuccess: () => { void qc.invalidateQueries({ queryKey: queryKeys.explainers.all }); },
  });
}
