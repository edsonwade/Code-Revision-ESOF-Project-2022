import httpClient from '@shared/api/httpClient';
import { buildUrl } from '@shared/api/apiConfig';
import type { Course, CreateCourseRequest } from '../model/course.types';

export const getCourses = async (): Promise<Course[]> =>
  (await httpClient.get<Course[]>(buildUrl('/courses'))).data;

export const getCourseById = async (id: number): Promise<Course> =>
  (await httpClient.get<Course>(buildUrl(`/courses/${id}`))).data;

export const createCourse = async (body: CreateCourseRequest): Promise<Course> =>
  (await httpClient.post<Course>(buildUrl('/courses/create'), body)).data;

export const updateCourse = async (id: number, body: CreateCourseRequest): Promise<Course> =>
  (await httpClient.put<Course>(buildUrl(`/courses/${id}`), body)).data;

export const deleteCourse = async (id: number): Promise<string> =>
  (await httpClient.delete<string>(buildUrl(`/courses/${id}`))).data;
