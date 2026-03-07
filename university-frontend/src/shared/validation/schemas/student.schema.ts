import { z } from 'zod';

// Synchronized with: ufp.esof.project.dto.student.StudentRequestDTO
// Fields: name (@NotBlank), email (@NotBlank @Email), phone (optional)
export const createStudentSchema = z.object({
  name: z.string().min(1, 'Student name is required').max(255),
  email: z.string().min(1, 'Email is required').email('Invalid email format'),
  phone: z.string().max(20).optional().or(z.literal('')),
});

export type CreateStudentFormData = z.infer<typeof createStudentSchema>;
