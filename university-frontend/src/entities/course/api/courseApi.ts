import httpClient from '@shared/api/httpClient';
import { buildUrl } from '@shared/api/apiConfig';
import type { Course, CreateCourseRequest } from '../model/course.types';

export const getCourses = async (): Promise<Course[]> =>
  (await httpClient.get<Course[]>(buildUrl('/course'))).data;
export const createCourse = async (body: CreateCourseRequest): Promise<Course> =>
  (await httpClient.post<Course>(buildUrl('/course'), body)).data;
export const updateCourse = async (id: number, body: CreateCourseRequest): Promise<Course> =>
  (await httpClient.put<Course>(buildUrl(`/course/${id}`), body)).data;
export const deleteCourse = async (id: number): Promise<string> =>
  (await httpClient.delete<string>(buildUrl(`/course/${id}`))).data;
