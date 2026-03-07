import type { AuditableEntity } from '@shared/types/common.types';

// Synchronized with: ufp.esof.project.dto.ReviewResponseDTO
// Last verified: 2026-03-07
export interface ReviewResponseDTO extends AuditableEntity {
  id: number;
  rating: number;
  comment: string;
  studentName: string;
  explainerName: string;
}

// Synchronized with: ufp.esof.project.dto.ReviewCreateDTO
// Fields: rating (@Min(1) @Max(5)), comment (@NotBlank @Size(10,500)),
//         appointmentId (@NotNull), studentId (@NotNull), explainerId (@NotNull)
export interface ReviewCreateDTO {
  rating: number;
  comment: string;
  appointmentId: number;
  studentId: number;
  explainerId: number;
}
