import { useQuery } from '@tanstack/react-query';
import { availabilityApi } from '../api/availabilityApi';
import { queryKeys } from '../../../shared/constants/queryKeys';

export const useAvailabilities = () => {
  return useQuery({
    queryKey: queryKeys.availabilities.all,
    queryFn: availabilityApi.getAvailabilities,
  });
};

export const useAvailability = (id: number) => {
  return useQuery({
    queryKey: queryKeys.availabilities.detail(id),
    queryFn: () => availabilityApi.getAvailabilityById(id),
    enabled: !!id,
  });
};
