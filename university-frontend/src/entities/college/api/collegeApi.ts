import httpClient from '@shared/api/httpClient';
import { buildUrl } from '@shared/api/apiConfig';
import type { College, CreateCollegeRequest } from '../model/college.types';

export const getColleges = async (): Promise<College[]> =>
  (await httpClient.get<College[]>(buildUrl('/colleges'))).data;

export const getCollegeById = async (id: number): Promise<College> =>
  (await httpClient.get<College>(buildUrl(`/colleges/${id}`))).data;

export const createCollege = async (body: CreateCollegeRequest): Promise<College> =>
  (await httpClient.post<College>(buildUrl('/colleges'), body)).data;

export const updateCollege = async (id: number, body: CreateCollegeRequest): Promise<College> =>
  (await httpClient.put<College>(buildUrl(`/colleges/${id}`), body)).data;

export const deleteCollege = async (id: number): Promise<void> => {
  await httpClient.delete(buildUrl(`/colleges/${id}`));
};
