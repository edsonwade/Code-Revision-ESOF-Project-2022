import type { AuditableEntity } from '@shared/types/common.types';

// Synchronized with: ufp.esof.project.dto.student.StudentResponseDTO
// Last verified: 2026-03-07
export interface Student extends AuditableEntity {
  id: number;
  name: string;
  email: string;
  phone?: string;
}

// Synchronized with: ufp.esof.project.dto.student.StudentRequestDTO
// Fields: name (@NotBlank), email (@NotBlank @Email), phone (optional)
export interface CreateStudentRequest {
  name: string;
  email: string;
  phone?: string;
}
