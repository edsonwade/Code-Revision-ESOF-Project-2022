import httpClient from '@shared/api/httpClient';
import { buildUrl } from '@shared/api/apiConfig';
import type { ReviewResponseDTO, ReviewCreateDTO } from '../model/review.types';
export const getReviews = async (): Promise<ReviewResponseDTO[]> => (await httpClient.get<ReviewResponseDTO[]>(buildUrl('/reviews'))).data;
export const createReview = async (body: ReviewCreateDTO): Promise<ReviewResponseDTO> => (await httpClient.post<ReviewResponseDTO>(buildUrl('/reviews'), body)).data;
