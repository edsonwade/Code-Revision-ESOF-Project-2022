import httpClient from '@shared/api/httpClient';
import { buildUrl } from '@shared/api/apiConfig';
import type { ExplainerDto, CreateExplainerRequest } from '../model/explainer.types';

export async function getExplainers(): Promise<ExplainerDto[]> {
  const { data } = await httpClient.get<ExplainerDto[]>(buildUrl('/explainer'));
  return data;
}

export async function getExplainerById(id: number): Promise<ExplainerDto> {
  const { data } = await httpClient.get<ExplainerDto>(buildUrl(`/explainer/${id}`));
  return data;
}

export async function createExplainer(body: CreateExplainerRequest): Promise<ExplainerDto> {
  const { data } = await httpClient.post<ExplainerDto>(buildUrl('/explainer/create'), body);
  return data;
}

export async function updateExplainer(id: number, body: CreateExplainerRequest): Promise<ExplainerDto> {
  const { data } = await httpClient.put<ExplainerDto>(buildUrl(`/explainer/update/${id}`), body);
  return data;
}

export async function deleteExplainer(id: number): Promise<string> {
  const { data } = await httpClient.delete<string>(buildUrl(`/explainer/delete/${id}`));
  return data;
}
