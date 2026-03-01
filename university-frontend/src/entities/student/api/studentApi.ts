import httpClient from '@shared/api/httpClient';
import { buildUrl } from '@shared/api/apiConfig';
import type { Student, CreateStudentRequest } from '../model/student.types';

export async function getStudents(): Promise<Student[]> {
  const { data } = await httpClient.get<Student[]>(buildUrl('/students'));
  return data;
}

export async function getStudentById(id: number): Promise<Student> {
  const { data } = await httpClient.get<Student>(buildUrl(`/students/${id}`));
  return data;
}

export async function searchStudentsByName(name: string): Promise<Student[]> {
  const { data } = await httpClient.get<Student[]>(
    buildUrl(`/students/search?name=${encodeURIComponent(name)}`)
  );
  return data;
}

export async function createStudent(body: CreateStudentRequest): Promise<Student> {
  const { data } = await httpClient.post<Student>(buildUrl('/students'), body);
  return data;
}

export async function updateStudent(id: number, body: CreateStudentRequest): Promise<Student> {
  const { data } = await httpClient.put<Student>(buildUrl(`/students/${id}`), body);
  return data;
}

export async function deleteStudent(id: number): Promise<string> {
  const { data } = await httpClient.delete<string>(buildUrl(`/students/${id}`));
  return data;
}
