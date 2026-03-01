import type { AuditableEntity } from '@shared/types/common.types';
export interface ReviewResponseDTO extends AuditableEntity {
  id: number;
  rating: number;
  comment?: string;
  studentId: number;
  studentName?: string;
  explainerId: number;
  explainerName?: string;
  organizationId: number;
}
export interface ReviewCreateDTO {
  rating: number;
  comment?: string;
  studentId: number;
  explainerId: number;
  organizationId?: number;
}
