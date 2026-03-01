import type { AuditableEntity } from '@shared/types/common.types';

// Synchronized with: ufp.esof.project.models.enums.Language
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

export interface ExplainerDto extends AuditableEntity {
  id: number;
  name: string;
  email: string;
  language: Language;
  bio?: string;
  organizationId: number;
}

export interface CreateExplainerRequest {
  name: string;
  email: string;
  language: Language;
  bio?: string;
  organizationId?: number;
}
