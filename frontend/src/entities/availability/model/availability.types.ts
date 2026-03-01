import { AuditableEntity } from '../../../shared/types/common.types';

export interface Availability extends AuditableEntity {
  id: number;
  dayOfWeek: number;
  startTime: string;
  endTime: string;
  explainerId: number;
}

export interface AvailabilityDto extends Availability {}
