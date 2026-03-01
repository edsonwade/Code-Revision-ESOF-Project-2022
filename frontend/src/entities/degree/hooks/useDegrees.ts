import { useQuery } from '@tanstack/react-query';
import { degreeApi } from '../api/degreeApi';
import { queryKeys } from '../../../shared/constants/queryKeys';

export const useDegrees = () => {
  return useQuery({
    queryKey: queryKeys.degrees.all,
    queryFn: degreeApi.getDegrees,
  });
};

export const useDegree = (id: number) => {
  return useQuery({
    queryKey: queryKeys.degrees.detail(id),
    queryFn: () => degreeApi.getDegreeById(id),
    enabled: !!id,
  });
};
