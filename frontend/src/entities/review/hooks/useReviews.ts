import { useQuery } from '@tanstack/react-query';
import { reviewApi } from '../api/reviewApi';
import { queryKeys } from '../../../shared/constants/queryKeys';

export const useReviews = () => {
  return useQuery({
    queryKey: queryKeys.reviews.all,
    queryFn: reviewApi.getReviews,
  });
};
