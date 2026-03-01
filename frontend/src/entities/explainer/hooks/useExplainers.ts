import { useQuery } from '@tanstack/react-query';
import { explainerApi } from '../api/explainerApi';
import { queryKeys } from '../../../shared/constants/queryKeys';

export const useExplainers = () => {
  return useQuery({
    queryKey: queryKeys.explainers.all,
    queryFn: explainerApi.getExplainers,
  });
};

export const useExplainer = (id: number) => {
  return useQuery({
    queryKey: queryKeys.explainers.detail(id),
    queryFn: () => explainerApi.getExplainerById(id),
    enabled: !!id,
  });
};
