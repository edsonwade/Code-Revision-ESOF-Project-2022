import { httpClient } from '../../../shared/api/httpClient';
import { Review, ReviewCreateDto } from '../model/review.types';

export const reviewApi = {
  getReviews: (): Promise<Review[]> => httpClient.get('/reviews'),
  createReview: (data: ReviewCreateDto): Promise<Review> => httpClient.post('/reviews', data),
};
