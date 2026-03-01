import { z } from 'zod';

export const createStudentSchema = z.object({
  name: z.string().min(2, 'Name must be at least 2 characters').max(100),
  email: z.string().email('Invalid email address'),
  studentNumber: z.string().min(3, 'Student number is required').max(20),
});

export type CreateStudentFormData = z.infer<typeof createStudentSchema>;
