import { httpClient } from '../../../shared/api/httpClient';
import { Degree, DegreeDto } from '../model/degree.types';

export const degreeApi = {
  getDegrees: (): Promise<Degree[]> => httpClient.get('/degrees'),
  getDegreeById: (id: number): Promise<Degree> => httpClient.get(`/degrees/${id}`),
  createDegree: (data: DegreeDto): Promise<Degree> => httpClient.post('/degrees', data),
  updateDegree: (id: number, data: DegreeDto): Promise<Degree> => httpClient.put(`/degrees/${id}`, data),
  deleteDegree: (id: number): Promise<string> => httpClient.delete(`/degrees/${id}`),
};
