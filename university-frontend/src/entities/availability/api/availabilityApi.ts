import httpClient from '@shared/api/httpClient';
import { buildUrl } from '@shared/api/apiConfig';
import type { AvailabilityDto, CreateAvailabilityRequest } from '../model/availability.types';
export const getAvailability = async (): Promise<AvailabilityDto[]> => (await httpClient.get<AvailabilityDto[]>(buildUrl('/availability'))).data;
export const createAvailability = async (body: CreateAvailabilityRequest): Promise<AvailabilityDto> => (await httpClient.post<AvailabilityDto>(buildUrl('/availability'), body)).data;
export const updateAvailability = async (id: number, body: CreateAvailabilityRequest): Promise<AvailabilityDto> => (await httpClient.put<AvailabilityDto>(buildUrl(`/availability/${id}`), body)).data;
export const deleteAvailability = async (id: number): Promise<string> => (await httpClient.delete<string>(buildUrl(`/availability/${id}`))).data;
