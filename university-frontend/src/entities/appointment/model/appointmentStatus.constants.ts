// Synchronized with: ufp.esof.project.models.enums.AppointmentStatus
// Last verified: 2026-02-28

export const APPOINTMENT_STATUS = {
  SCHEDULED: 'SCHEDULED',
  COMPLETED: 'COMPLETED',
  CANCELLED: 'CANCELLED',
  RESCHEDULED: 'RESCHEDULED',
  NO_SHOW: 'NO_SHOW',
} as const;

export type AppointmentStatus =
  (typeof APPOINTMENT_STATUS)[keyof typeof APPOINTMENT_STATUS];

export const APPOINTMENT_STATUS_LABELS: Record<AppointmentStatus, string> = {
  SCHEDULED: 'Scheduled',
  COMPLETED: 'Completed',
  CANCELLED: 'Cancelled',
  RESCHEDULED: 'Rescheduled',
  NO_SHOW: 'No Show',
};

// Mirrors AppointmentStatus.canBeRescheduled()
export const canBeRescheduled = (status: AppointmentStatus): boolean =>
  status === 'SCHEDULED' || status === 'RESCHEDULED';

// Mirrors AppointmentStatus.canBeCancelled()
export const canBeCancelled = (status: AppointmentStatus): boolean =>
  status === 'SCHEDULED' || status === 'RESCHEDULED';

// Mirrors AppointmentStatus.isFinalState()
export const isFinalState = (status: AppointmentStatus): boolean =>
  ['COMPLETED', 'CANCELLED', 'NO_SHOW'].includes(status);
