import httpClient from '@shared/api/httpClient';
import { buildUrl } from '@shared/api/apiConfig';
import type { College, CreateCollegeRequest } from '../model/college.types';

export const getColleges = async (): Promise<College[]> =>
  (await httpClient.get<College[]>(buildUrl('/college'))).data;

export const createCollege = async (body: CreateCollegeRequest): Promise<College> =>
  (await httpClient.post<College>(buildUrl('/college'), body)).data;

export const updateCollege = async (id: number, body: CreateCollegeRequest): Promise<College> =>
  (await httpClient.put<College>(buildUrl(`/college/${id}`), body)).data;

export const deleteCollege = async (id: number): Promise<string> =>
  (await httpClient.delete<string>(buildUrl(`/college/${id}`))).data;
