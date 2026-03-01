import type { AuditableEntity } from '@shared/types/common.types';

// Synchronized with: ufp.esof.project.models.Student
export interface Student extends AuditableEntity {
  id: number;
  name: string;
  email: string;
  studentNumber: string;
  organizationId: number;
}

export interface CreateStudentRequest {
  name: string;
  email: string;
  studentNumber: string;
  organizationId?: number;
}

export interface StudentResponse {
  id: number;
  name: string;
  email: string;
  studentNumber: string;
}
