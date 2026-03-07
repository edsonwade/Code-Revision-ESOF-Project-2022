import type { AuditableEntity } from '@shared/types/common.types';

// Synchronized with: ufp.esof.project.models.enums.Language
// Last verified: 2026-03-07
export const LANGUAGE = {
  PORTUGUESE: 'PORTUGUESE',
  ENGLISH: 'ENGLISH',
  SPANISH: 'SPANISH',
  ITALIAN: 'ITALIAN',
} as const;

export type Language = typeof LANGUAGE[keyof typeof LANGUAGE];

export const LANGUAGE_LABELS: Record<Language, string> = {
  PORTUGUESE: 'Portuguese',
  ENGLISH: 'English',
  SPANISH: 'Spanish',
  ITALIAN: 'Italian',
};

// Synchronized with: ufp.esof.project.dto.explainer.ExplainerResponseDTO
// Last verified: 2026-03-07
export interface ExplainerDto extends AuditableEntity {
  id: number;
  name: string;
  email: string;
  phone?: string;
  availabilities?: ExplainerAvailabilityDto[];
}

export interface ExplainerAvailabilityDto {
  id: number;
  startTime: string;
  endTime: string;
}

// Synchronized with: ufp.esof.project.dto.explainer.ExplainerRequestDTO
// Fields: name (@NotBlank), email (@NotBlank @Email), phone (optional)
export interface CreateExplainerRequest {
  name: string;
  email: string;
  phone?: string;
}
