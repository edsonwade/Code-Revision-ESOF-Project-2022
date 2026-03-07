import httpClient from '@shared/api/httpClient';
import { buildUrl } from '@shared/api/apiConfig';
import type { AvailabilityDto, CreateAvailabilityRequest } from '../model/availability.types';

export const getAvailabilities = async (): Promise<AvailabilityDto[]> =>
  (await httpClient.get<AvailabilityDto[]>(buildUrl('/availabilities'))).data;

export const getAvailabilityById = async (id: number): Promise<AvailabilityDto> =>
  (await httpClient.get<AvailabilityDto>(buildUrl(`/availabilities/${id}`))).data;

export const createAvailability = async (body: CreateAvailabilityRequest): Promise<AvailabilityDto> =>
  (await httpClient.post<AvailabilityDto>(buildUrl('/availabilities'), body)).data;

export const updateAvailability = async (id: number, body: CreateAvailabilityRequest): Promise<AvailabilityDto> =>
  (await httpClient.put<AvailabilityDto>(buildUrl(`/availabilities/${id}`), body)).data;

export const deleteAvailability = async (id: number): Promise<string> =>
  (await httpClient.delete<string>(buildUrl(`/availabilities/${id}`))).data;
