import type { AuditableEntity } from '@shared/types/common.types';

export interface College extends AuditableEntity {
  id: number;
  name: string;
  code: string;
  organizationId: number;
}

export interface CreateCollegeRequest {
  name: string;
  code: string;
  organizationId?: number;
}
