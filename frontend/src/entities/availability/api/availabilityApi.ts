import { httpClient } from '../../../shared/api/httpClient';
import { Availability, AvailabilityDto } from '../model/availability.types';

export const availabilityApi = {
  getAvailabilities: (): Promise<Availability[]> => httpClient.get('/availabilities'),
  getAvailabilityById: (id: number): Promise<Availability> => httpClient.get(`/availabilities/${id}`),
  createAvailability: (data: AvailabilityDto): Promise<Availability> => httpClient.post('/availabilities', data),
  updateAvailability: (id: number, data: AvailabilityDto): Promise<Availability> => httpClient.put(`/availabilities/${id}`, data),
  deleteAvailability: (id: number): Promise<string> => httpClient.delete(`/availabilities/${id}`),
};
