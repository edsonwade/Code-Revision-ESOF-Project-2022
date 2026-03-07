import { z } from 'zod';

// Synchronized with: ufp.esof.project.dto.request.CreateAppointmentRequest
// Fields: studentId (@NotNull @Positive), explainerId (@NotNull @Positive),
//         courseId (@NotNull @Positive), startTime (@NotNull @Future), endTime (@NotNull @Future)
export const createAppointmentSchema = z.object({
  studentId: z.number().min(1, 'Student is required'),
  explainerId: z.number().min(1, 'Explainer is required'),
  courseId: z.number().min(1, 'Course is required'),
  startTime: z.string().min(1, 'Start time is required'),
  endTime: z.string().min(1, 'End time is required'),
});

export type CreateAppointmentFormData = z.infer<typeof createAppointmentSchema>;
