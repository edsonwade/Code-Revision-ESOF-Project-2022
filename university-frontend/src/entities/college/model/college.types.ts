import type { AuditableEntity } from '@shared/types/common.types';

// Synchronized with: ufp.esof.project.dto.college.CollegeResponseDTO
// Last verified: 2026-03-07
export interface College extends AuditableEntity {
  id: number;
  name: string;
  organizationId: number;
  degrees?: CollegeDegreeDto[];
}

export interface CollegeDegreeDto {
  id: number;
  name: string;
}

// Synchronized with: ufp.esof.project.dto.college.CollegeRequestDTO
// Fields: name (@NotBlank), degreeIds (optional Set<Long>)
export interface CreateCollegeRequest {
  name: string;
  degreeIds?: number[];
}
