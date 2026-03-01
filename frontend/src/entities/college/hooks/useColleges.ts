import { useQuery } from '@tanstack/react-query';
import { collegeApi } from '../api/collegeApi';
import { queryKeys } from '../../../shared/constants/queryKeys';

export const useColleges = () => {
  return useQuery({
    queryKey: queryKeys.colleges.all,
    queryFn: collegeApi.getColleges,
  });
};

export const useCollege = (id: number) => {
  return useQuery({
    queryKey: ['colleges', 'detail', id], // Not in factory yet but useful
    queryFn: () => collegeApi.getCollegeById(id),
    enabled: !!id,
  });
};
