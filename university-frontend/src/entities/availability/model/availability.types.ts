import type { AuditableEntity } from '@shared/types/common.types';
export interface AvailabilityDto extends AuditableEntity {
  id: number;
  explainerId: number;
  dayOfWeek: string;
  startTime: string;
  endTime: string;
  organizationId: number;
}
export interface CreateAvailabilityRequest { explainerId: number; dayOfWeek: string; startTime: string; endTime: string; organizationId?: number; }
