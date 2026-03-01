import type { AuditableEntity } from '@shared/types/common.types';
export interface Degree extends AuditableEntity {
  id: number;
  name: string;
  acronym: string;
  organizationId: number;
}
export interface CreateDegreeRequest { name: string; acronym: string; organizationId?: number; }
