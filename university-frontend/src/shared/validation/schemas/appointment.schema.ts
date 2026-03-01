import { z } from 'zod';

export const createAppointmentSchema = z.object({
  studentId: z.number().min(1, 'Student is required'),
  explainerId: z.number().min(1, 'Explainer is required'),
  scheduledAt: z.string().min(1, 'Date and time is required'),
  notes: z.string().max(500).optional(),
});

export type CreateAppointmentFormData = z.infer<typeof createAppointmentSchema>;
