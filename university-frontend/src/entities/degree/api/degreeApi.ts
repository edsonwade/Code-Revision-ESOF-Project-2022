import httpClient from '@shared/api/httpClient';
import { buildUrl } from '@shared/api/apiConfig';
import type { Degree, CreateDegreeRequest } from '../model/degree.types';

export const getDegrees = async (): Promise<Degree[]> =>
  (await httpClient.get<Degree[]>(buildUrl('/degrees'))).data;

export const getDegreeById = async (id: number): Promise<Degree> =>
  (await httpClient.get<Degree>(buildUrl(`/degrees/${id}`))).data;

export const createDegree = async (body: CreateDegreeRequest): Promise<Degree> =>
  (await httpClient.post<Degree>(buildUrl('/degrees'), body)).data;

export const updateDegree = async (id: number, body: CreateDegreeRequest): Promise<Degree> =>
  (await httpClient.put<Degree>(buildUrl(`/degrees/${id}`), body)).data;

export const deleteDegree = async (id: number): Promise<string> =>
  (await httpClient.delete<string>(buildUrl(`/degrees/${id}`))).data;
