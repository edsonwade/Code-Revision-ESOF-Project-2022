import { AuditableEntity } from '../../../shared/types/common.types';

export const APPOINTMENT_STATUS = {
  SCHEDULED: 'SCHEDULED',
  COMPLETED: 'COMPLETED',
  CANCELLED: 'CANCELLED',
  RESCHEDULED: 'RESCHEDULED',
  NO_SHOW: 'NO_SHOW',
} as const;

export type AppointmentStatus = typeof APPOINTMENT_STATUS[keyof typeof APPOINTMENT_STATUS];

export interface Appointment extends AuditableEntity {
  id: number;
  startTime: string;
  endTime: string;
  status: AppointmentStatus;
  studentId: number;
  explainerId: number;
}

export interface CreateAppointmentRequest {
  startTime: string;
  endTime: string;
  studentId: number;
  explainerId: number;
}

export interface AppointmentDto extends Appointment {}
export interface AppointmentResponse extends Appointment {}

export const canBeRescheduled = (status: AppointmentStatus): boolean =>
  status === 'SCHEDULED' || status === 'RESCHEDULED';

export const canBeCancelled = (status: AppointmentStatus): boolean =>
  status === 'SCHEDULED' || status === 'RESCHEDULED';

export const isFinalState = (status: AppointmentStatus): boolean =>
  ['COMPLETED', 'CANCELLED', 'NO_SHOW'].includes(status);
