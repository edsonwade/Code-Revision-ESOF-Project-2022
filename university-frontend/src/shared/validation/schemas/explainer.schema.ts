import { z } from 'zod';

export const createExplainerSchema = z.object({
  name: z.string().min(2, 'Name must be at least 2 characters').max(100),
  email: z.string().email('Invalid email address'),
  language: z.enum(['PORTUGUESE', 'ENGLISH', 'SPANISH', 'ITALIAN']),
  bio: z.string().max(500).optional(),
});

export type CreateExplainerFormData = z.infer<typeof createExplainerSchema>;
