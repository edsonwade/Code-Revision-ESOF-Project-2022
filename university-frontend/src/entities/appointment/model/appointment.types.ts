import type { AuditableEntity } from '@shared/types/common.types';

// Synchronized with: ufp.esof.project.models.enums.AppointmentStatus
// Last verified: 2026-03-07
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

// Synchronized with: ufp.esof.project.dto.response.AppointmentResponse
// Last verified: 2026-03-07
export interface AppointmentDto extends AuditableEntity {
  id: number;
  studentId: number | null;
  studentName: string | null;
  explainerId: number | null;
  explainerName: string | null;
  courseId: number | null;
  courseName: string | null;
  startTime: string;
  endTime: string;
  status: AppointmentStatus;
}

// Synchronized with: ufp.esof.project.dto.request.CreateAppointmentRequest
// Fields: studentId (@NotNull @Positive), explainerId (@NotNull @Positive),
//         courseId (@NotNull @Positive), startTime (@NotNull @Future), endTime (@NotNull @Future)
export interface CreateAppointmentRequest {
  studentId: number;
  explainerId: number;
  courseId: number;
  startTime: string;
  endTime: string;
}
