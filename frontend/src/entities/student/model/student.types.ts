import { AuditableEntity } from '../../../shared/types/common.types';

export interface Student extends AuditableEntity {
  id: number;
  name: string;
  email: string;
}

export interface CreateStudentRequest {
  name: string;
  email: string;
}

export interface StudentResponse {
  id: number;
  name: string;
  email: string;
}
