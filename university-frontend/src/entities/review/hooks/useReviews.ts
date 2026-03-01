import { useQuery } from '@tanstack/react-query';
import { queryKeys } from '@shared/constants/queryKeys';
import { getReviews } from '../api/reviewApi';
export function useReviews() {
  return useQuery({ queryKey: queryKeys.reviews.all, queryFn: getReviews, staleTime: 5 * 60 * 1000 });
}
