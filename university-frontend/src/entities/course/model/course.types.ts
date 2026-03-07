import type { AuditableEntity } from '@shared/types/common.types';

// Synchronized with: ufp.esof.project.dto.course.CourseResponseDTO
// Last verified: 2026-03-07
export interface Course extends AuditableEntity {
  id: number;
  name: string;
  degreeId: number | null;
  degreeName: string | null;
}

// Synchronized with: ufp.esof.project.dto.course.CourseRequestDTO
// Fields: name (@NotBlank), degreeId (optional Long)
export interface CreateCourseRequest {
  name: string;
  degreeId?: number;
}
