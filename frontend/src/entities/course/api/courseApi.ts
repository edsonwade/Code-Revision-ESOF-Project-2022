import { httpClient } from '../../../shared/api/httpClient';
import { Course, CourseDto } from '../model/course.types';

export const courseApi = {
  getCourses: (): Promise<Course[]> => httpClient.get('/courses'),
  getCourseById: (id: number): Promise<Course> => httpClient.get(`/courses/${id}`),
  createCourse: (data: CourseDto): Promise<Course> => httpClient.post('/courses/create', data),
  updateCourse: (id: number, data: CourseDto): Promise<Course> => httpClient.put(`/courses/${id}`, data),
  deleteCourse: (id: number): Promise<string> => httpClient.delete(`/courses/${id}`),
};
