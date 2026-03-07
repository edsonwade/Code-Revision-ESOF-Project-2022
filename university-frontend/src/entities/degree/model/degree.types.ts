import type { AuditableEntity } from '@shared/types/common.types';

// Synchronized with: ufp.esof.project.dto.degree.DegreeResponseDTO
// Last verified: 2026-03-07
export interface Degree extends AuditableEntity {
  id: number;
  name: string;
  collegeId: number | null;
  collegeName: string | null;
  courses?: DegreeCourseDto[];
}

export interface DegreeCourseDto {
  id: number;
  name: string;
}

// Synchronized with: ufp.esof.project.dto.degree.DegreeRequestDTO
// Fields: name (@NotBlank), collegeId (optional Long)
export interface CreateDegreeRequest {
  name: string;
  collegeId?: number;
}
