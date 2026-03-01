import { Badge } from '@shared/components/ui/badge';
// Synchronized with: ufp.esof.project.models.enums.AppointmentStatus
export type AppointmentStatus =
  | 'SCHEDULED'
  | 'COMPLETED'
  | 'CANCELLED'
  | 'RESCHEDULED'
  | 'NO_SHOW';

const statusConfig: Record<AppointmentStatus, { label: string; variant: 'default' | 'success' | 'danger' | 'warning' | 'secondary' | 'cyan' }> = {
  SCHEDULED: { label: 'Scheduled', variant: 'cyan' },
  COMPLETED: { label: 'Completed', variant: 'success' },
  CANCELLED: { label: 'Cancelled', variant: 'danger' },
  RESCHEDULED: { label: 'Rescheduled', variant: 'warning' },
  NO_SHOW: { label: 'No Show', variant: 'secondary' },
};

interface StatusBadgeProps {
  status: AppointmentStatus;
}

export function StatusBadge({ status }: StatusBadgeProps) {
  const config = statusConfig[status] ?? { label: status, variant: 'secondary' as const };
  return <Badge variant={config.variant}>{config.label}</Badge>;
}
