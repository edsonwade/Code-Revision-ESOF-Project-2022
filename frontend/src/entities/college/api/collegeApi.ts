import { httpClient } from '../../../shared/api/httpClient';
import { College, CollegeDto } from '../model/college.types';

export const collegeApi = {
  getColleges: (): Promise<College[]> => httpClient.get('/colleges'),
  getCollegeById: (id: number): Promise<College> => httpClient.get(`/colleges/${id}`),
  createCollege: (data: CollegeDto): Promise<College> => httpClient.post('/colleges', data),
  updateCollege: (id: number, data: CollegeDto): Promise<College> => httpClient.put(`/colleges/${id}`, data),
  deleteCollege: (id: number): Promise<void> => httpClient.delete(`/colleges/${id}`),
};
