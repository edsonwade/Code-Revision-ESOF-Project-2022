import { z } from 'zod';

// Synchronized with: ufp.esof.project.dto.explainer.ExplainerRequestDTO
// Fields: name (@NotBlank), email (@NotBlank @Email), phone (optional)
export const createExplainerSchema = z.object({
  name: z.string().min(1, 'Explainer name is required').max(255),
  email: z.string().min(1, 'Email is required').email('Invalid email format'),
  phone: z.string().max(20).optional().or(z.literal('')),
});

export type CreateExplainerFormData = z.infer<typeof createExplainerSchema>;
