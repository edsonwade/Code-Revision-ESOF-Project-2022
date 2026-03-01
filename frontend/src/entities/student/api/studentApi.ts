import { httpClient } from '../../../shared/api/httpClient';
import { Student, CreateStudentRequest } from '../model/student.types';

export const studentApi = {
  getStudents: (): Promise<Student[]> => httpClient.get('/students'),
  getStudentById: (id: number): Promise<Student> => httpClient.get(`/students/${id}`),
  searchStudents: (name: string): Promise<Student[]> => httpClient.get(`/students/search?name=${name}`),
  createStudent: (data: CreateStudentRequest): Promise<Student> => httpClient.post('/students/create', data),
  updateStudent: (id: number, data: CreateStudentRequest): Promise<Student> => httpClient.put(`/students/${id}`, data),
  deleteStudent: (id: number): Promise<string> => httpClient.delete(`/students/${id}`),
};
