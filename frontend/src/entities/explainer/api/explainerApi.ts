import { httpClient } from '../../../shared/api/httpClient';
import { Explainer, ExplainerDto } from '../model/explainer.types';

export const explainerApi = {
  getExplainers: (): Promise<Explainer[]> => httpClient.get('/explainers'),
  getExplainerById: (id: number): Promise<Explainer> => httpClient.get(`/explainers/${id}`),
  createExplainer: (data: ExplainerDto): Promise<Explainer> => httpClient.post('/explainers/create', data),
  updateExplainer: (id: number, data: ExplainerDto): Promise<Explainer> => httpClient.put(`/explainers/update/${id}`, data),
  deleteExplainer: (id: number): Promise<string> => httpClient.delete(`/explainers/delete/${id}`),
};
