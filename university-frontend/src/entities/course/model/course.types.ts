import type { AuditableEntity } from '@shared/types/common.types';
export interface Course extends AuditableEntity {
  id: number;
  name: string;
  code: string;
  collegeId?: number;
  organizationId: number;
}
export interface CreateCourseRequest { name: string; code: string; collegeId?: number; organizationId?: number; }
