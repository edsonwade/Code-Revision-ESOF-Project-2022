import httpClient from '@shared/api/httpClient';
import { buildUrl } from '@shared/api/apiConfig';
import type { Degree, CreateDegreeRequest } from '../model/degree.types';
export const getDegrees = async (): Promise<Degree[]> => (await httpClient.get<Degree[]>(buildUrl('/degree'))).data;
export const createDegree = async (body: CreateDegreeRequest): Promise<Degree> => (await httpClient.post<Degree>(buildUrl('/degree'), body)).data;
export const updateDegree = async (id: number, body: CreateDegreeRequest): Promise<Degree> => (await httpClient.put<Degree>(buildUrl(`/degree/${id}`), body)).data;
export const deleteDegree = async (id: number): Promise<string> => (await httpClient.delete<string>(buildUrl(`/degree/${id}`))).data;
