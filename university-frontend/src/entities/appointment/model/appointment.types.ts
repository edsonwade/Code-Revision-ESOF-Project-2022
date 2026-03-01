import type { AuditableEntity } from '@shared/types/common.types';

// Synchronized with: ufp.esof.project.models.enums.AppointmentStatus
export const APPOINTMENT_STATUS = {
  SCHEDULED: 'SCHEDULED',
  COMPLETED: 'COMPLETED',
  CANCELLED: 'CANCELLED',
  RESCHEDULED: 'RESCHEDULED',
  NO_SHOW: 'NO_SHOW',
} as const;

export type AppointmentStatus = typeof APPOINTMENT_STATUS[keyof typeof APPOINTMENT_STATUS];

export const canBeRescheduled = (s: AppointmentStatus): boolean =>
  s === 'SCHEDULED' || s === 'RESCHEDULED';
export const canBeCancelled = (s: AppointmentStatus): boolean =>
  s === 'SCHEDULED' || s === 'RESCHEDULED';
export const isFinalState = (s: AppointmentStatus): boolean =>
  ['COMPLETED', 'CANCELLED', 'NO_SHOW'].includes(s);

export interface AppointmentDto extends AuditableEntity {
  id: number;
  studentId: number;
  explainerId: number;
  scheduledAt: string;
  status: AppointmentStatus;
  notes?: string;
  organizationId: number;
}

export interface CreateAppointmentRequest {
  studentId: number;
  explainerId: number;
  scheduledAt: string;
  notes?: string;
  organizationId?: number;
}

export interface AppointmentResponse {
  id: number;
  studentName: string;
  explainerName: string;
  scheduledAt: string;
  status: AppointmentStatus;
  notes?: string;
}
