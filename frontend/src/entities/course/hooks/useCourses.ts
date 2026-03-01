import { useQuery } from '@tanstack/react-query';
import { courseApi } from '../api/courseApi';
import { queryKeys } from '../../../shared/constants/queryKeys';

export const useCourses = () => {
  return useQuery({
    queryKey: queryKeys.courses.all,
    queryFn: courseApi.getCourses,
  });
};

export const useCourse = (id: number) => {
  return useQuery({
    queryKey: ['courses', 'detail', id], // Not in factory yet but useful
    queryFn: () => courseApi.getCourseById(id),
    enabled: !!id,
  });
};
