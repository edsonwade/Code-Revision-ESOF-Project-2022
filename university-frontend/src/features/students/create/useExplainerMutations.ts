import { useMutation, useQueryClient } from '@tanstack/react-query';
import { queryKeys } from '@shared/constants/queryKeys';
import {
  createExplainer,
  updateExplainer,
  deleteExplainer,
} from '@entities/explainer/api/explainerApi';
import type { ExplainerDto } from '@entities/explainer/model/explainer.types';

export function useCreateExplainer() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (data: ExplainerDto) => createExplainer(data),
    onSuccess: () => {
      void queryClient.invalidateQueries({ queryKey: queryKeys.explainers.all });
    },
  });
}

export function useUpdateExplainer(id: number) {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (data: ExplainerDto) => updateExplainer(id, data),
    onSuccess: () => {
      void queryClient.invalidateQueries({ queryKey: queryKeys.explainers.all });
      void queryClient.invalidateQueries({ queryKey: queryKeys.explainers.detail(id) });
    },
  });
}

export function useDeleteExplainer() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (id: number) => deleteExplainer(id),
    onSuccess: () => {
      void queryClient.invalidateQueries({ queryKey: queryKeys.explainers.all });
    },
  });
}
